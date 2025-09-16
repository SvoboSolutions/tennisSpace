package org.tennis.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.domain.model.User
import org.tennis.space.presentation.auth.login.LoginScreen
import org.tennis.space.presentation.auth.register.RegisterScreen
import org.tennis.space.presentation.club.search.ClubSearchScreen
import org.tennis.space.presentation.main.MainScreen
import org.tennis.space.presentation.theme.TennisSpaceTheme

@Composable
fun TennisApp() {
    val authRepository: AuthRepository = koinInject()
    var user by remember { mutableStateOf<User?>(null) }
    var showRegister by remember { mutableStateOf(false) }
    var showClubSearch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        user = authRepository.getCurrentUser()
    }

    TennisSpaceTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    user == null -> {
                        AppHeader()
                        Spacer(modifier = Modifier.height(32.dp))

                        if (showRegister) {
                            RegisterScreen(
                                onRegisterSuccess = { registeredUser ->
                                    user = registeredUser
                                    showRegister = false

                                },
                                onBackToLogin = { showRegister = false }
                            )
                        } else {
                            LoginScreen(
                                onLoginSuccess = { loggedInUser ->
                                    user = loggedInUser

                                },
                                onNavigateToRegister = { showRegister = true }
                            )
                        }
                    }

                    showClubSearch -> {
                        ClubSearchScreen(
                            onClubSelected = { clubId ->
                                // Navigation zu Club Details später
                                println("Selected club: $clubId")
                            }
                        )
                    }

                    else -> {

                        MainScreen(
                            user = user!!,
                            onLogout = {
                                user = null
                                showClubSearch = false
                            },
                            onSearchClubs = { showClubSearch = true }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 32.dp)
    ) {
        Text(
            text = "Tennis Space",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Dein Platz. Deine Zeit.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}