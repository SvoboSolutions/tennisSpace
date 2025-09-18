package org.tennis.space.presentation.club.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.presentation.theme.TennisDimensions

@Composable
fun ClubDetailSheet(
    club: TennisClub,
    onJoinClub: (String) -> Unit, // Ändert sich zu clubId Parameter
    onDismiss: () -> Unit
) {
    var isJoining by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val authRepository: AuthRepository = koinInject()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TennisDimensions.SpaceLarge),
        verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceMedium)
    ) {
        Text(
            text = club.name,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = club.address,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = club.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(TennisDimensions.CardCornerRadius)
        ) {
            Column(
                modifier = Modifier.padding(TennisDimensions.SpaceMedium),
                verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceSmall)
            ) {
                Text(
                    text = "Plätze",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${club.courts.size} Tennisplätze verfügbar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(TennisDimensions.SpaceSmall))

        Button(
            onClick = {
                scope.launch {
                    isJoining = true
                    // Hier wird der Club dem User hinzugefügt
                    onJoinClub(club.id)
                    isJoining = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(TennisDimensions.InputFieldHeight),
            enabled = !isJoining,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(TennisDimensions.CardCornerRadius)
        ) {
            if (isJoining) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Verein beitreten",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(TennisDimensions.SpaceLarge))
    }
}