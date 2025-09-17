package org.tennis.space.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object TennisColors {
    // Clay Court Hauptpalette - Orange-basierte Ascheplatz-Farben
    val ClayOrange = Color(0xFFD2691E)            // Primär: Warmes Ascheplatz-Orange
    val ClayOrangeLight = Color(0xFFE8A971)       // Heller Aschenton
    val ClayBrown = Color(0xFFB8860B)             // Sekundär: Goldbraun
    val ClayBrownLight = Color(0xFFD4A843)        // Helles Goldbraun

    val CourtWhite = Color(0xFFFFFFF8)            // Weiß der Linien
    val TennisGreen = Color(0xFF228B22)           // Saftiges Grün für Akzente
    val CourtBlue = Color(0xFF4682B4)             // Harmonisches Stahlblau
    val TennisYellow = Color(0xFFFFD700)          // Leuchtend gelb für Bälle

    // Natürliche harmonische Töne
    val WarmCream = Color(0xFFFAF0E6)             // Warme cremige Basis
    val SoftPeach = Color(0xFFFFE4B5)             // Sanftes Pfirsich
    val EarthGreen = Color(0xFF556B2F)            // Gedämpftes Erdgrün

    // Neutrale Palette
    val CharcoalGray = Color(0xFF36454F)          // Dunkles Grau
    val WarmGray = Color(0xFF8B8680)              // Warmes Grau
    val LightGray = Color(0xFFE5E5DC)             // Helles Grau (Beige-Ton)
    val SoftWhite = Color(0xFFFFFEFC)             // Warmes Weiß

    // Text Colors Light
    val TextPrimary = Color(0xFF2F1B14)           // Sehr dunkles Braun
    val TextSecondary = Color(0xFF5D4037)         // Mittleres Braun
    val TextTertiary = Color(0xFF8D6E63)          // Helleres Braun
    val TextAccent = ClayOrange                   // Orange für Akzente
    val TextOnColor = CourtWhite                  // Weiß auf dunklen Farben

    // Text Colors Dark
    val TextPrimaryDark = Color(0xFFFAF0E6)       // Sehr helles Creme
    val TextSecondaryDark = Color(0xFFE8A971)     // Helles Orange
    val TextTertiaryDark = Color(0xFFBFA48E)      // Mittleres Beige
    val TextAccentDark = ClayOrangeLight          // Helles Orange für Dark Mode
    val TextOnColorDark = CharcoalGray            // Dunkel auf hellen Farben

    // Surface Colors Light
    val SurfaceLight = CourtWhite
    val SurfaceVariantLight = WarmCream
    val BackgroundLight = Color(0xFFFFFBF0)       // Sehr warmer cremiger Hintergrund

    // Surface Colors Dark
    val SurfaceDark = Color(0xFF1C1611)           // Warmes dunkles Braun
    val SurfaceVariantDark = Color(0xFF2A241E)    // Warmes mittleres Braun
    val BackgroundDark = Color(0xFF12100C)        // Warmes Tiefbraun

    // Status Colors - Harmonische Palette
    val Success = TennisGreen                     // Grün für Erfolg
    val SuccessDark = Color(0xFF32CD32)
    val Error = Color(0xFFDC143C)                 // Kräftiges Rot für Fehler
    val ErrorDark = Color(0xFFFF6B6B)
    val Warning = TennisYellow                    // Gelb für Warnungen
    val WarningDark = Color(0xFFFFE135)
    val Info = CourtBlue                          // Blau für Info
    val InfoDark = Color(0xFF87CEEB)

    // Court Status - Clay Court Farben
    val Available = TennisGreen                   // Grün = verfügbar
    val Booked = ClayOrange                       // Orange = gebucht
    val Maintenance = WarmGray                    // Grau = Wartung
}

// Light Theme - Clay Court Orange-Palette
val LightColorScheme = lightColorScheme(
    primary = TennisColors.ClayOrange,
    onPrimary = TennisColors.CourtWhite,
    primaryContainer = Color(0xFFFFF4E6),         // Sehr helles Orange
    onPrimaryContainer = Color(0xFF3D1A00),

    secondary = TennisColors.TennisGreen,
    onSecondary = TennisColors.CourtWhite,
    secondaryContainer = Color(0xFFE8F5E8),       // Sehr helles Grün
    onSecondaryContainer = Color(0xFF0D2818),

    tertiary = TennisColors.CourtBlue,
    onTertiary = TennisColors.CourtWhite,
    tertiaryContainer = Color(0xFFE1F4FF),        // Sehr helles Blau
    onTertiaryContainer = Color(0xFF001D35),

    background = TennisColors.BackgroundLight,
    onBackground = TennisColors.TextPrimary,

    surface = TennisColors.SurfaceLight,
    onSurface = TennisColors.TextPrimary,
    surfaceVariant = TennisColors.SurfaceVariantLight,
    onSurfaceVariant = TennisColors.TextSecondary,

    outline = TennisColors.WarmGray.copy(alpha = 0.6f),
    outlineVariant = TennisColors.LightGray,

    error = TennisColors.Error,
    onError = TennisColors.CourtWhite,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFF5F2120),

    scrim = Color.Black.copy(alpha = 0.32f)
)

// Dark Theme - Warme Clay-Dunkelfarben
val DarkColorScheme = darkColorScheme(
    primary = TennisColors.ClayOrangeLight,       // Helles Orange als Primär
    onPrimary = TennisColors.TextOnColorDark,
    primaryContainer = Color(0xFF3D1A00),         // Dunkles Orange
    onPrimaryContainer = Color(0xFFFFF4E6),

    secondary = Color(0xFF32CD32),                // Helles Grün
    onSecondary = TennisColors.TextOnColorDark,
    secondaryContainer = Color(0xFF0D2818),       // Dunkles Grün
    onSecondaryContainer = Color(0xFFE8F5E8),

    tertiary = Color(0xFF87CEEB),                 // Helles Blau
    onTertiary = TennisColors.TextOnColorDark,
    tertiaryContainer = Color(0xFF001D35),        // Dunkles Blau
    onTertiaryContainer = Color(0xFFE1F4FF),

    background = TennisColors.BackgroundDark,
    onBackground = TennisColors.TextPrimaryDark,

    surface = TennisColors.SurfaceDark,
    onSurface = TennisColors.TextPrimaryDark,
    surfaceVariant = TennisColors.SurfaceVariantDark,
    onSurfaceVariant = TennisColors.TextSecondaryDark,

    outline = Color(0xFF8B8680),
    outlineVariant = Color(0xFF4A4037),

    error = TennisColors.ErrorDark,
    onError = TennisColors.TextOnColorDark,
    errorContainer = Color(0xFF5F2120),
    onErrorContainer = Color(0xFFFFEBEE),

    scrim = Color.Black.copy(alpha = 0.32f),

    // Erweiterte Dark Mode Colors
    surfaceTint = TennisColors.ClayOrangeLight,
    inverseSurface = Color(0xFFE8DDD4),
    inverseOnSurface = Color(0xFF1C1611),
    inversePrimary = TennisColors.ClayOrange,

    surfaceDim = Color(0xFF0C0A08),
    surfaceBright = Color(0xFF3A322A),
    surfaceContainer = Color(0xFF1F1C17),
    surfaceContainerHigh = Color(0xFF2A251F),
    surfaceContainerHighest = Color(0xFF342F28),
    surfaceContainerLow = Color(0xFF171410),
    surfaceContainerLowest = Color(0xFF12100C)
)

// Clay Court Gradienten
object TennisGradients {
    val ClayGradient = listOf(
        TennisColors.ClayOrange,
        TennisColors.ClayBrown
    )

    val CourtGradient = listOf(
        TennisColors.TennisGreen,
        TennisColors.CourtBlue
    )

    val SunsetGradient = listOf(
        TennisColors.ClayOrange,
        TennisColors.TennisYellow
    )
}