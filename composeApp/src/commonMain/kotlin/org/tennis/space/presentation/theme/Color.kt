package org.tennis.space.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object TennisColors {
    // Moderne Tennis Palette - Hauptfarben
    val TennisGreen = Color(0xFF2E7D32)           // (Sekundär, dezenter Akzent)
    val TennisGreenLight = Color(0xFF66BB6A)      // Heller Grün-Akzent
    val LimeAccent = Color(0xFFFFF176)            // Ball-Gelb bleibt als Akzent
    val CourtRed = Color(0xFFF4511E)              // Orange (Clay Court)
    val NetWhite = Color(0xFFFFFFFF)              // Rein Weiß (Netz)
    val CourtBlue = Color(0xFF1565C0)             // Hard Court Blau

    // Neutrale Farben
    val ShadowGray = Color(0xFF424242)            // Schatten
    val LightGray = Color(0xFFE0E0E0)             // Helles Grau
    val MediumGray = Color(0xFF9E9E9E)            // Mittleres Grau
    val DarkGray = Color(0xFF212121)              // Dunkles Grau

    // Light Mode Text
    val TextPrimary = Color(0xFF1B1B1B)           // Fast Schwarz
    val TextSecondary = Color(0xFF444444)         // Abdunkeln für besseren Kontrast
    val TextTertiary = Color(0xFF777777)
    val TextOnColor = Color.White                 // Weiß auf Farbe

    // Dark Mode Text
    val TextPrimaryDark = Color(0xFFE0E0E0)
    val TextSecondaryDark = Color(0xFFB0B0B0)
    val TextTertiaryDark = Color(0xFF888888)
    val TextOnColorDark = Color(0xFF121212)

    // Surface Colors Light
    val SurfaceLight = Color(0xFFFFFFFF)          // Weiß
    val SurfaceVariantLight = Color(0xFFFAFAFA)   // Sehr hell
    val BackgroundLight = Color(0xFFFFFBF8)       // Warmes Weiß mit leichter Clay-Note

    // Surface Colors Dark
    val SurfaceDark = Color(0xFF121212)
    val SurfaceVariantDark = Color(0xFF1E1E1E)
    val BackgroundDark = Color(0xFF0D0D0D)

    // Status Colors
    val Success = Color(0xFF42A5F5)               // Blau = Positiv
    val SuccessDark = Color(0xFF64B5F6)           // Hellblau für Dark Mode
    val Error = Color(0xFFE53935)                 // Rot (Fehler bleibt rot)
    val ErrorDark = Color(0xFFEF5350)
    val Warning = Color(0xFFFF9800)               // Orange
    val WarningDark = Color(0xFFFFB74D)

    // Court Status
    val Available = Color(0xFF42A5F5)             // Blau = frei
    val Booked = Color(0xFFFF9800)                // Orange = reserviert
    val Blocked = Color(0xFF757575)               // Grau = blockiert
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