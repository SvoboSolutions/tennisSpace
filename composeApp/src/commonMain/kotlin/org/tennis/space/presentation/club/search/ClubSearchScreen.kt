package org.tennis.space.presentation.club.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.presentation.shared.ErrorContent
import org.tennis.space.presentation.shared.LoadingContent
import org.tennis.space.presentation.theme.TennisColors

@OptIn(FlowPreview::class)
@Composable
fun ClubSearchScreen(
    onClubSelected: (String) -> Unit = {}
) {
    val clubRepository: ClubRepository = koinInject()
    var uiState by remember { mutableStateOf(ClubSearchUiState()) }
    val scope = rememberCoroutineScope()

    // Debounced search flow
    val searchFlow = remember { MutableStateFlow("") }

    LaunchedEffect(Unit) {
        // Load clubs initially
        loadClubs(clubRepository) { newState ->
            uiState = newState
        }

        // Setup debounced search
        searchFlow
            .debounce(300)
            .distinctUntilChanged()
            .collect { query ->
                uiState = uiState.copy(
                    filteredClubs = filterClubs(uiState.allClubs, query)
                )
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header - Mit Theme-Farben
        Text(
            text = "Tennis Clubs finden",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface // ✅ Theme-Farbe
        )

        // Enhanced Search Bar
        EnhancedSearchBar(
            query = uiState.searchQuery,
            onQueryChange = { query ->
                uiState = uiState.copy(searchQuery = query)
                scope.launch {
                    searchFlow.emit(query)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Content based on state
        ClubContent(
            uiState = uiState,
            onClubSelected = onClubSelected,
            onRetry = {
                scope.launch {
                    loadClubs(clubRepository) { newState ->
                        uiState = newState
                    }
                }
            }
        )
    }
}

// UI State data class for better state management
private data class ClubSearchUiState(
    val allClubs: List<TennisClub> = emptyList(),
    val filteredClubs: List<TennisClub> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@Composable
private fun ClubContent(
    uiState: ClubSearchUiState,
    onClubSelected: (String) -> Unit,
    onRetry: () -> Unit
) {
    when {
        uiState.isLoading -> {
            LoadingContent()
        }

        uiState.errorMessage != null -> {
            ErrorContent(
                message = uiState.errorMessage,
                onRetry = onRetry
            )
        }

        uiState.filteredClubs.isEmpty() && uiState.searchQuery.isNotEmpty() -> {
            EmptySearchContent(query = uiState.searchQuery)
        }

        uiState.filteredClubs.isEmpty() -> {
            EmptyStateContent()
        }

        else -> {
            ClubList(
                clubs = uiState.filteredClubs,
                onClubSelected = onClubSelected
            )
        }
    }
}

@Composable
private fun EmptyStateContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "🎾",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Keine Clubs verfügbar",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface // ✅ Theme-Farbe
            )
            Text(
                text = "Bitte versuchen Sie es später erneut",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant // ✅ Theme-Farbe
            )
        }
    }
}

@Composable
private fun ClubList(
    clubs: List<TennisClub>,
    onClubSelected: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = clubs,
            key = { it.id }
        ) { club ->
            EnhancedClubCard(
                club = club,
                onClick = { onClubSelected(club.id) }
            )
        }
    }
}

private suspend fun loadClubs(
    repository: ClubRepository,
    onStateChange: (ClubSearchUiState) -> Unit
) {
    onStateChange(ClubSearchUiState(isLoading = true))

    val result = repository.getAllClubs()
    result.onSuccess { clubs ->
        onStateChange(
            ClubSearchUiState(
                allClubs = clubs,
                filteredClubs = clubs,
                isLoading = false
            )
        )
    }.onFailure { error ->
        onStateChange(
            ClubSearchUiState(
                isLoading = false,
                errorMessage = error.message ?: "Unbekannter Fehler"
            )
        )
    }
}

private fun filterClubs(clubs: List<TennisClub>, query: String): List<TennisClub> {
    if (query.isEmpty()) return clubs

    return clubs.filter { club ->
        club.name.contains(query, ignoreCase = true) ||
                club.address.contains(query, ignoreCase = true) ||
                club.description.contains(query, ignoreCase = true)
    }
}