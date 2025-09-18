package org.tennis.space.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoorFront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.presentation.club.search.ClubCard
import org.tennis.space.presentation.club.search.ClubSearchScreen
import org.tennis.space.presentation.navigation.TennisBottomNavigation
import org.tennis.space.presentation.theme.TennisDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    user: User,
    onLogout: () -> Unit,
    onUserUpdated: (User) -> Unit
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
                            imageVector = Icons.Outlined.DoorFront,
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
                onClubSelected = { clubId ->
                    // Navigation ändert sich automatisch da user.ownClubs nicht mehr leer ist
                    // Bleibt auf Dashboard, aber BottomNav zeigt jetzt "Platz buchen" etc.
                },
                modifier = Modifier.padding(paddingValues)
            )

            "profile" -> ProfileContent(
                user = user,
                modifier = Modifier.padding(paddingValues)
            )

            "search_club" -> SearchClubContent(
                modifier = Modifier.padding(paddingValues),
                user = user,
                onUserUpdated = { onUserUpdated;currentRoute = "dashboard" },
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
    onClubSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val clubRepository: ClubRepository = koinInject()
    var userClubs by remember { mutableStateOf<List<TennisClub>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(user.ownClubs) {
        if (user.ownClubs.isNotEmpty()) {
            isLoading = true
            // Load club details for user's clubs
            clubRepository.getAllClubs()
                .onSuccess { allClubs ->
                    userClubs = allClubs.filter { club ->
                        user.ownClubs.contains(club.id)
                    }
                }
            isLoading = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(TennisDimensions.SpaceMedium),
        verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceMedium)
    ) {
        WelcomeHeader(user = user)

        when {
            user.ownClubs.isEmpty() -> {
                Text(
                    text = "Noch kein Verein beigetreten",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            isLoading -> {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }

            else -> {
                Text(
                    text = "Meine Vereine",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceSmall)
                ) {
                    items(userClubs, key = { it.id }) { club ->
                        ClubCard(
                            club = club,
                            onClick = { onClubSelected(club.id) }
                        )
                    }
                }
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
private fun SearchClubContent(
    user: User,
    modifier: Modifier = Modifier,
    onUserUpdated: (User) -> Unit,
) {
    ClubSearchScreen(
        modifier = modifier,
        onClubSelected = { clubId ->
            val updatedUser = user.copy(
                ownClubs = user.ownClubs + clubId
            )
            onUserUpdated(updatedUser)
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