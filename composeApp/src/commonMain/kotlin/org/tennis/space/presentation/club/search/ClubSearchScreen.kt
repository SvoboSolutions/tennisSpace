package org.tennis.space.presentation.club.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.presentation.theme.TennisDimensions

@Composable
fun ClubSearchScreen(
    onClubSelected: (String) -> Unit = {},
    modifier: Modifier
) {
    val clubRepository: ClubRepository = koinInject()
    var uiState by remember { mutableStateOf(ClubSearchUiState()) }
    val scope = rememberCoroutineScope()

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

            uiState.errorMessage != null -> {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    shape = RoundedCornerShape(TennisDimensions.CardCornerRadius)
                ) {
                    Column(
                        modifier = Modifier.padding(TennisDimensions.SpaceMedium),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceSmall)
                    ) {
                        Text(
                            text = "Fehler",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        uiState.errorMessage?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    uiState = uiState.copy(isLoading = true, errorMessage = null)
                                    clubRepository.getAllClubs()
                                        .onSuccess { clubs ->
                                            uiState = uiState.copy(
                                                allClubs = clubs,
                                                filteredClubs = clubs,
                                                isLoading = false
                                            )
                                        }
                                        .onFailure { error ->
                                            uiState = uiState.copy(
                                                errorMessage = error.message,
                                                isLoading = false
                                            )
                                        }
                                }
                            }
                        ) {
                            Text("Erneut versuchen")
                        }
                    }
                }
            }

            uiState.filteredClubs.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.searchQuery.isEmpty()) "Keine Clubs verfügbar"
                        else "Keine Clubs gefunden für \"${uiState.searchQuery}\"",
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
                            onClick = { onClubSelected(club.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ClubCard(
    club: TennisClub,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = TennisDimensions.CardElevation),
        shape = RoundedCornerShape(TennisDimensions.CardCornerRadius)
    ) {
        Column(
            modifier = Modifier.padding(TennisDimensions.SpaceMedium),
            verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceSmall)
        ) {
            Text(
                text = club.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = club.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = club.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
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