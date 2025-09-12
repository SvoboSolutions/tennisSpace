package org.tennis.space.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object TennisColors {
    // Moderne Tennis Palette - Hauptfarben
    val TennisGreen = Color(0xFF2E7D32)           // Tennis Court Grün
    val TennisGreenLight = Color(0xFF4CAF50)      // Helleres Grün
    val LimeAccent = Color(0xFF8BC34A)            // Tennis Ball Grün
    val CourtRed = Color(0xFFD32F2F)              // Clay Court Rot
    val NetWhite = Color(0xFFF8F9FA)              // Netz Weiß
    val CourtBlue = Color(0xFF1976D2)             // Hard Court Blau

    // Neutrale Farben
    val ShadowGray = Color(0xFF424242)            // Schatten
    val LightGray = Color(0xFFE0E0E0)             // Helles Grau
    val MediumGray = Color(0xFF9E9E9E)            // Mittleres Grau
    val DarkGray = Color(0xFF212121)              // Dunkles Grau

    // Light Mode Text
    val TextPrimary = Color(0xFF1B1B1B)           // Fast Schwarz
    val TextSecondary = Color(0xFF666666)         // Mittelgrau
    val TextTertiary = Color(0xFF9E9E9E)          // Hellgrau
    val TextOnColor = Color.White                 // Weiß auf Farbe

    // Dark Mode Text
    val TextPrimaryDark = Color(0xFFE0E0E0)       // Helles Grau
    val TextSecondaryDark = Color(0xFFB0B0B0)     // Mittleres Grau
    val TextTertiaryDark = Color(0xFF757575)      // Dunkles Grau
    val TextOnColorDark = Color(0xFF1B1B1B)       // Dunkel auf Farbe

    // Surface Colors Light
    val SurfaceLight = Color(0xFFFFFFFF)          // Reines Weiß
    val SurfaceVariantLight = Color(0xFFF5F5F5)   // Sehr helles Grau
    val BackgroundLight = Color(0xFFFAFAFA)       // Warmes Weiß

    // Surface Colors Dark
    val SurfaceDark = Color(0xFF1E1E1E)           // Sehr dunkles Grau
    val SurfaceVariantDark = Color(0xFF2D2D2D)    // Mittleres Dunkelgrau
    val BackgroundDark = Color(0xFF121212)        // Fast Schwarz

    // Status Colors
    val Success = Color(0xFF4CAF50)               // Grün
    val SuccessDark = Color(0xFF81C784)           // Heller für Dark Mode
    val Error = Color(0xFFE53935)                 // Rot
    val ErrorDark = Color(0xFFEF5350)             // Heller für Dark Mode
    val Warning = Color(0xFFFF9800)               // Orange
    val WarningDark = Color(0xFFFFB74D)           // Heller für Dark Mode

    // Court Status (für später)
    val Available = Color(0xFF4CAF50)
    val Booked = Color(0xFFFF9800)
    val Blocked = Color(0xFFE53935)
}

// Light Theme
val LightColorScheme = lightColorScheme(
    primary = TennisColors.TennisGreen,
    onPrimary = TennisColors.TextOnColor,
    primaryContainer = Color(0xFFE8F5E9),         // Sehr helles Grün
    onPrimaryContainer = TennisColors.TennisGreen,

    secondary = TennisColors.LimeAccent,
    onSecondary = TennisColors.TextPrimary,
    secondaryContainer = Color(0xFFF1F8E9),       // Sehr helles Lime
    onSecondaryContainer = Color(0xFF33691E),

    tertiary = TennisColors.CourtBlue,
    onTertiary = TennisColors.TextOnColor,
    tertiaryContainer = Color(0xFFE3F2FD),        // Sehr helles Blau
    onTertiaryContainer = Color(0xFF0D47A1),

    background = TennisColors.BackgroundLight,
    onBackground = TennisColors.TextPrimary,

    surface = TennisColors.SurfaceLight,
    onSurface = TennisColors.TextPrimary,
    surfaceVariant = TennisColors.SurfaceVariantLight,
    onSurfaceVariant = TennisColors.TextSecondary,

    outline = TennisColors.MediumGray,
    outlineVariant = TennisColors.LightGray,

    error = TennisColors.Error,
    onError = TennisColors.TextOnColor,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),

    scrim = Color.Black.copy(alpha = 0.32f)
)

// Dark Theme
val DarkColorScheme = darkColorScheme(
    primary = TennisColors.LimeAccent,            // Helleres Grün für Dark Mode
    onPrimary = TennisColors.TextOnColorDark,
    primaryContainer = Color(0xFF1B5E20),         // Dunkles Grün
    onPrimaryContainer = Color(0xFFC8E6C9),

    secondary = TennisColors.TennisGreenLight,
    onSecondary = TennisColors.TextOnColorDark,
    secondaryContainer = Color(0xFF2E7D32),
    onSecondaryContainer = Color(0xFFA5D6A7),

    tertiary = Color(0xFF64B5F6),                 // Helles Blau für Dark Mode
    onTertiary = TennisColors.TextOnColorDark,
    tertiaryContainer = Color(0xFF1565C0),
    onTertiaryContainer = Color(0xFFBBDEFB),

    background = TennisColors.BackgroundDark,
    onBackground = TennisColors.TextPrimaryDark,

    surface = TennisColors.SurfaceDark,
    onSurface = TennisColors.TextPrimaryDark,
    surfaceVariant = TennisColors.SurfaceVariantDark,
    onSurfaceVariant = TennisColors.TextSecondaryDark,

    outline = Color(0xFF8A8A8A),
    outlineVariant = Color(0xFF484848),

    error = TennisColors.ErrorDark,
    onError = TennisColors.TextOnColorDark,
    errorContainer = Color(0xFFB71C1C),
    onErrorContainer = Color(0xFFFFCDD2),

    scrim = Color.Black.copy(alpha = 0.32f),

    // Erweiterte Dark Mode Colors
    surfaceTint = TennisColors.LimeAccent,
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF1B1B1B),
    inversePrimary = TennisColors.TennisGreen,

    surfaceDim = Color(0xFF111111),
    surfaceBright = Color(0xFF373737),
    surfaceContainer = Color(0xFF1E1E1E),
    surfaceContainerHigh = Color(0xFF2D2D2D),
    surfaceContainerHighest = Color(0xFF373737),
    surfaceContainerLow = Color(0xFF1B1B1B),
    surfaceContainerLowest = Color(0xFF0F0F0F)
)