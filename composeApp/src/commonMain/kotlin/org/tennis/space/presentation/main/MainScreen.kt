package org.tennis.space.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.presentation.club.search.ClubSearchScreen
import org.tennis.space.presentation.navigation.TennisBottomNavigation
import org.tennis.space.presentation.theme.TennisDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    user: User,
    onLogout: () -> Unit,
) {
    var currentRoute by remember { mutableStateOf("dashboard") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (user.ownClubs.isEmpty()) "Tennis Space" else user.ownClubs.first(),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Abmelden",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            TennisBottomNavigation(
                user = user,
                currentRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                }
            )
        }
    ) { paddingValues ->
        when (currentRoute) {
            "dashboard" -> DashboardContent(
                user = user,
                onLogout = onLogout,
                modifier = Modifier.padding(paddingValues)
            )
            "profile" -> ProfileContent(
                user = user,
                modifier = Modifier.padding(paddingValues)
            )
            "search_club" -> SearchClubContent(
                modifier = Modifier.padding(paddingValues)
            )
            "search_training" -> SearchTrainingContent(
                modifier = Modifier.padding(paddingValues)
            )
            "book_court" -> BookCourtContent(
                modifier = Modifier.padding(paddingValues)
            )

        }
    }
}

@Composable
private fun DashboardContent(
    user: User,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authRepository: AuthRepository = koinInject()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TennisDimensions.SpaceLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceXLarge)
        ) {
            WelcomeHeader(user = user)

            if (user.ownClubs.isEmpty()) {
                ClubSearchButton(
                    onSearchClubs = { /* Navigate to club search */ }
                )
            } else {
                DashboardActions(user = user)
            }
        }
    }
}

@Composable
private fun ClubSearchButton(onSearchClubs: () -> Unit) {
    Button(
        onClick = onSearchClubs,
        modifier = Modifier
            .fillMaxWidth()
            .height(TennisDimensions.InputFieldHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = "Tennisverein suchen",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun DashboardActions(user: User) {
    Column(
        verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceMedium)
    ) {
        Text(
            text = "Willkommen bei ${user.ownClubs.first()}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = { /* Navigate to court booking */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(TennisDimensions.InputFieldHeight)
        ) {
            Text("Platz buchen")
        }
    }
}

@Composable
private fun ProfileContent(
    user: User,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Profil - ${user.name}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun SearchClubContent(modifier: Modifier = Modifier) {
    ClubSearchScreen(
        modifier = modifier, // Falls ClubSearchScreen einen modifier Parameter hat
        onClubSelected = { clubId ->
            // Hier können Sie später die Club-Details Navigation hinzufügen
            println("Selected club: $clubId")
        }
    )
}

@Composable
private fun SearchTrainingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Training suchen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun BookCourtContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Platz buchen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}