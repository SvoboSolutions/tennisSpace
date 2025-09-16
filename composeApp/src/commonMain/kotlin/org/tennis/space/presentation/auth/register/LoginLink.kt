package org.tennis.space.presentation.auth.register

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.tennis.space.presentation.theme.TennisColors

@Composable
fun LoginLink(
    onBackToLogin: () -> Unit,
    enabled: Boolean
) {
    TextButton(
        onClick = onBackToLogin,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = TennisColors.ForestGreen
        )
    ) {
        Text(
            text = "Schon ein Konto? Jetzt anmelden",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}