package org.tennis.space.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object TennisColors {
    // Hauptfarbpalette - Inspiriert von Tennis Elementen
    val ForestGreen = Color(0xFF2E5233)           // Primär: Rasenplatz Grün
    val ForestGreenLight = Color(0xFF4A7C59)      // Heller Rasenplatz
    val ClayOrange = Color(0xFFE07B39)            // Sekundär: Sandplatz Orange
    val ClayOrangeLight = Color(0xFFEE9B5B)       // Heller Sandplatz

    val TennisYellow = Color(0xFFE6D35C)          // Tennisball Gelb (gedämpft)
    val CourtBlue = Color(0xFF4A90A4)             // Hartplatz Blau (gedämpft)
    val NetWhite = Color(0xFFFAFAFA)              // Netz Weiß

    // Natürliche Akzentfarben
    val WarmBeige = Color(0xFFF5F1E8)             // Warmer Hintergrund
    val SoftMint = Color(0xFFB8E6B8)              // Sanftes Mint
    val DeepTeal = Color(0xFF2B6777)              // Tiefes Petrol

    // Neutrale Palette
    val CharcoalGray = Color(0xFF2C2C2C)          // Dunkles Grau
    val SlateGray = Color(0xFF64748B)             // Mittleres Grau
    val MistGray = Color(0xFFCBD5E0)              // Helles Grau
    val SoftWhite = Color(0xFFFFFEFC)             // Warmes Weiß

    // Text Colors Light
    val TextPrimary = Color(0xFF1A202C)           // Sehr dunkles Blau-Grau
    val TextSecondary = Color(0xFF4A5568)         // Mittleres Grau
    val TextTertiary = Color(0xFF718096)          // Helleres Grau
    val TextAccent = ForestGreen                  // Grün für Akzente
    val TextOnColor = SoftWhite                   // Weiß auf dunklen Farben

    // Text Colors Dark
    val TextPrimaryDark = Color(0xFFF7FAFC)       // Sehr helles Grau
    val TextSecondaryDark = Color(0xFFE2E8F0)     // Helles Grau
    val TextTertiaryDark = Color(0xFFA0AEC0)      // Mittleres Grau
    val TextAccentDark = SoftMint                 // Mint für Dark Mode
    val TextOnColorDark = CharcoalGray            // Dunkel auf hellen Farben

    // Surface Colors Light
    val SurfaceLight = SoftWhite
    val SurfaceVariantLight = WarmBeige
    val BackgroundLight = Color(0xFFFCFBF9)       // Noch wärmerer Hintergrund

    // Surface Colors Dark
    val SurfaceDark = Color(0xFF1A1D23)           // Warmes Dunkelgrau
    val SurfaceVariantDark = Color(0xFF232830)    // Warmes mittleres Grau
    val BackgroundDark = Color(0xFF0F1114)        // Warmes Tiefschwarz

    // Status Colors - Harmonisch mit der Hauptpalette
    val Success = Color(0xFF38A169)               // Grün (harmoniert mit Forest)
    val SuccessDark = Color(0xFF68D391)
    val Error = Color(0xFFE53E3E)                 // Rot (gedämpft)
    val ErrorDark = Color(0xFFFC8181)
    val Warning = ClayOrange                      // Orange aus Hauptpalette
    val WarningDark = ClayOrangeLight
    val Info = CourtBlue                          // Blau aus Hauptpalette
    val InfoDark = Color(0xFF63B3ED)

    // Court Status - Natürliche Farben
    val Available = Color(0xFF38A169)             // Grün = verfügbar
    val Booked = ClayOrange                       // Orange = gebucht
    val Maintenance = SlateGray                   // Grau = Wartung
}

// Light Theme - Harmonische Tennis Farben
val LightColorScheme = lightColorScheme(
    primary = TennisColors.ForestGreen,
    onPrimary = TennisColors.TextOnColor,
    primaryContainer = Color(0xFFE8F4EA),         // Sehr helles Grün
    onPrimaryContainer = Color(0xFF1B3A1F),

    secondary = TennisColors.ClayOrange,
    onSecondary = TennisColors.TextOnColor,
    secondaryContainer = Color(0xFFFDF2E9),       // Sehr helles Orange
    onSecondaryContainer = Color(0xFF7A2E0F),

    tertiary = TennisColors.CourtBlue,
    onTertiary = TennisColors.TextOnColor,
    tertiaryContainer = Color(0xFFE8F4F8),        // Sehr helles Blau
    onTertiaryContainer = Color(0xFF1A3A42),

    background = TennisColors.BackgroundLight,
    onBackground = TennisColors.TextPrimary,

    surface = TennisColors.SurfaceLight,
    onSurface = TennisColors.TextPrimary,
    surfaceVariant = TennisColors.SurfaceVariantLight,
    onSurfaceVariant = TennisColors.TextSecondary,

    outline = TennisColors.SlateGray.copy(alpha = 0.6f),
    outlineVariant = TennisColors.MistGray,

    error = TennisColors.Error,
    onError = TennisColors.TextOnColor,
    errorContainer = Color(0xFFFED7D7),
    onErrorContainer = Color(0xFF742A2A),

    scrim = Color.Black.copy(alpha = 0.32f)
)

// Dark Theme - Warme, natürliche Dunkelfarben
val DarkColorScheme = darkColorScheme(
    primary = TennisColors.SoftMint,              // Sanftes Mint als Primär
    onPrimary = TennisColors.TextOnColorDark,
    primaryContainer = Color(0xFF1B3A1F),         // Dunkles Grün
    onPrimaryContainer = Color(0xFFB8E6B8),

    secondary = TennisColors.ClayOrangeLight,     // Helles Orange
    onSecondary = TennisColors.TextOnColorDark,
    secondaryContainer = Color(0xFF7A2E0F),       // Dunkles Orange
    onSecondaryContainer = Color(0xFFFDF2E9),

    tertiary = Color(0xFF87CEEB),                 // Helles Blau
    onTertiary = TennisColors.TextOnColorDark,
    tertiaryContainer = Color(0xFF1A3A42),        // Dunkles Blau
    onTertiaryContainer = Color(0xFFE8F4F8),

    background = TennisColors.BackgroundDark,
    onBackground = TennisColors.TextPrimaryDark,

    surface = TennisColors.SurfaceDark,
    onSurface = TennisColors.TextPrimaryDark,
    surfaceVariant = TennisColors.SurfaceVariantDark,
    onSurfaceVariant = TennisColors.TextSecondaryDark,

    outline = Color(0xFF8E8E93),
    outlineVariant = Color(0xFF48484A),

    error = TennisColors.ErrorDark,
    onError = TennisColors.TextOnColorDark,
    errorContainer = Color(0xFF742A2A),
    onErrorContainer = Color(0xFFFED7D7),

    scrim = Color.Black.copy(alpha = 0.32f),

    // Erweiterte Dark Mode Colors
    surfaceTint = TennisColors.SoftMint,
    inverseSurface = Color(0xFFE5E5E7),
    inverseOnSurface = Color(0xFF1C1C1E),
    inversePrimary = TennisColors.ForestGreen,

    surfaceDim = Color(0xFF141518),
    surfaceBright = Color(0xFF3A3A3C),
    surfaceContainer = Color(0xFF1E1E20),
    surfaceContainerHigh = Color(0xFF2C2C2E),
    surfaceContainerHighest = Color(0xFF3A3A3C),
    surfaceContainerLow = Color(0xFF1A1A1C),
    surfaceContainerLowest = Color(0xFF0F0F10)
)

// Zusätzliche Utility Extensions
val TennisColors.isLight: Boolean get() = true // Kann später dynamisch gemacht werden

// Gradient Definitionen für besondere UI Elemente
object TennisGradients {
    val CourtGradient = listOf(
        TennisColors.ForestGreen,
        TennisColors.ForestGreenLight
    )

    val SunsetGradient = listOf(
        TennisColors.ClayOrange,
        TennisColors.TennisYellow
    )

    val OceanGradient = listOf(
        TennisColors.CourtBlue,
        TennisColors.DeepTeal
    )
}