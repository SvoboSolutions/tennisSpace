package org.tennis.space.presentation.auth.login

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.tennis.space.presentation.theme.TennisColors

@Composable
fun RegisterLink(
    onNavigateToRegister: () -> Unit,
    enabled: Boolean
) {
    TextButton(
        onClick = onNavigateToRegister,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = TennisColors.ForestGreen
        )
    ) {
        Text(
            text = "Noch kein Konto? Jetzt registrieren",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}