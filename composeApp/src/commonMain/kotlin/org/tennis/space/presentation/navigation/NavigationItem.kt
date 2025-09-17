package org.tennis.space.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    // Immer verfügbar
    object Dashboard : NavigationItem("dashboard", "Home", Icons.Default.Home)
    object Profile : NavigationItem("profile", "Profil", Icons.Default.Person)

    // No Club Navigation
    object SearchTraining : NavigationItem("search_training", "Training suchen", Icons.Default.Search)
    object SearchClub : NavigationItem("search_club", "Verein suchen", Icons.Default.LocationOn)

    // Has Club Navigation
    object BookCourt : NavigationItem("book_court", "Platz buchen", Icons.Default.DateRange)
}

object NavigationItems {
    val noClubItems = listOf(
        NavigationItem.Dashboard,
        NavigationItem.SearchClub,
        NavigationItem.Profile
    )

    val hasClubItems = listOf(
        NavigationItem.Dashboard,
        NavigationItem.BookCourt,
        NavigationItem.Profile
    )
}