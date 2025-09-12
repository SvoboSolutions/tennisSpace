package org.tennis.space.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TennisSpaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Automatische Erkennung
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TennisTypography,
        content = content
    )
}