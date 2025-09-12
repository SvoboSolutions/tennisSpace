package org.tennis.space.presentation.club

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.presentation.theme.TennisColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

@Composable
fun ClubSearchScreen(
    onClubSelected: (String) -> Unit = {}
) {
    val clubRepository: ClubRepository = koinInject()
    var clubs by remember { mutableStateOf<List<TennisClub>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Load all clubs on start
    LaunchedEffect(Unit) {
        isLoading = true
        val result = clubRepository.getAllClubs()
        result.onSuccess { loadedClubs ->
            clubs = loadedClubs
        }.onFailure { error ->
            errorMessage = error.message
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tennis Clubs finden",
            style = MaterialTheme.typography.headlineMedium,
            color = TennisColors.TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
                // Search on text change
                scope.launch {
                    if (query.isNotEmpty()) {
                        val result = clubRepository.searchClubs(query)
                        result.onSuccess { searchResults ->
                            clubs = searchResults
                        }
                    } else {
                        // Reload all clubs if search is empty
                        val result = clubRepository.getAllClubs()
                        result.onSuccess { allClubs ->
                            clubs = allClubs
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Content
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = TennisColors.TennisGreen)
                }
            }

            errorMessage != null -> {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = TennisColors.Error.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = errorMessage!!,
                        color = TennisColors.Error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            clubs.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Keine Clubs gefunden",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TennisColors.TextSecondary
                    )
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(clubs) { club ->
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
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Club Name oder Ort suchen...") },
        leadingIcon = {
//            Icon(
//                imageVector = Icons,
//                contentDescription = "Suchen",
//                tint = TennisColors.TextSecondary
//            )
        },
        modifier = modifier,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TennisColors.TennisGreen,
            unfocusedBorderColor = TennisColors.TextSecondary,
            cursorColor = TennisColors.TennisGreen
        )
    )
}

@Composable
private fun ClubCard(
    club: TennisClub,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = club.name,
                style = MaterialTheme.typography.headlineSmall,
                color = TennisColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = club.address,
                style = MaterialTheme.typography.bodyMedium,
                color = TennisColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = club.description,
                style = MaterialTheme.typography.bodySmall,
                color = TennisColors.TextTertiary,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Court Info
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CourtInfoChip(
                    text = "${club.courts.size} Plätze",
                    color = TennisColors.TennisGreen
                )

                val hasIndoor = club.courts.any { it.isIndoor }
                if (hasIndoor) {
                    CourtInfoChip(
                        text = "Indoor",
                        color = TennisColors.CourtBlue
                    )
                }

                val hasFloodlights = club.courts.any { it.hasFloodlights }
                if (hasFloodlights) {
                    CourtInfoChip(
                        text = "Flutlicht",
                        color = TennisColors.LimeAccent
                    )
                }
            }
        }
    }
}

@Composable
private fun CourtInfoChip(
    text: String,
    color: androidx.compose.ui.graphics.Color
) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
        border = null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}