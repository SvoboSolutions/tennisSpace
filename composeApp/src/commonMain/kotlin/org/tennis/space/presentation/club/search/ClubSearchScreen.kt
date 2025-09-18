package org.tennis.space.presentation.club.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.presentation.theme.TennisDimensions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ClubSearchScreen(
    onClubSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clubRepository: ClubRepository = koinInject()
    var uiState by remember { mutableStateOf(ClubSearchUiState()) }
    var selectedClub by remember { mutableStateOf<TennisClub?>(null) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        uiState = uiState.copy(isLoading = true)
        clubRepository.getAllClubs()
            .onSuccess { clubs ->
                uiState = uiState.copy(allClubs = clubs, filteredClubs = clubs, isLoading = false)
            }
            .onFailure { error ->
                uiState = uiState.copy(errorMessage = error.message, isLoading = false)
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(TennisDimensions.SpaceMedium),
        verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceMedium)
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { query ->
                uiState = uiState.copy(
                    searchQuery = query,
                    filteredClubs = if (query.isEmpty()) uiState.allClubs
                    else uiState.allClubs.filter { club ->
                        club.name.contains(query, ignoreCase = true) ||
                                club.address.contains(query, ignoreCase = true) ||
                                club.description.contains(query, ignoreCase = true)
                    }
                )
            },
            label = { Text("Club suchen...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(TennisDimensions.CardCornerRadius),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            uiState.filteredClubs.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.searchQuery.isEmpty()) "Keine Clubs verfügbar"
                        else "Keine Clubs gefunden",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceSmall),
                    contentPadding = PaddingValues(vertical = TennisDimensions.SpaceSmall)
                ) {
                    items(uiState.filteredClubs, key = { it.id }) { club ->
                        ClubCard(
                            club = club,
                            onClick = {
                                selectedClub = club
                            }
                        )
                    }
                }
            }
        }
    }

    // Bottom Sheet
    selectedClub?.let { club ->
        ModalBottomSheet(
            onDismissRequest = { selectedClub = null },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            ClubDetailSheet(
                club = club,
                onJoinClub = {
                    scope.launch {
                        selectedClub = null
                        onClubSelected(club.id)
                    }
                },
                onDismiss = { selectedClub = null }
            )
        }
    }
}

private data class ClubSearchUiState(
    val allClubs: List<TennisClub> = emptyList(),
    val filteredClubs: List<TennisClub> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)