package org.tennis.space.presentation.club.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.presentation.theme.TennisColors

@Composable
fun CourtInfoRow(club: TennisClub) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Anzahl Plätze - Primärfarbe
        CourtInfoChip(
            text = "${club.courts.size} Plätze",
            icon = "🎾",
            color = TennisColors.ForestGreen
        )

        if (club.courts.any { it.isIndoor }) {
            CourtInfoChip(
                text = "Indoor",
                icon = "🏢",
                color = TennisColors.CourtBlue
            )
        }

        if (club.courts.any { it.hasFloodlights }) {
            CourtInfoChip(
                text = "Flutlicht",
                icon = "💡",
                color = TennisColors.TennisYellow
            )
        }
    }
}