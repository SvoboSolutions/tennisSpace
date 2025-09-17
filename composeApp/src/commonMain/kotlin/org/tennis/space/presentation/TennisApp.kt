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

    LaunchedEffect(Unit) {
        user = authRepository.getCurrentUser()
    }

    TennisSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                user == null -> {
                    AuthFlow(
                        showRegister = showRegister,
                        onShowRegisterChange = { showRegister = it },
                        onUserAuthenticated = { authenticatedUser ->
                            user = authenticatedUser
                        }
                    )
                }

                else -> {
                    MainScreen(
                        user = user!!,
                        onLogout = {
                            user = null
                            showRegister = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthFlow(
    showRegister: Boolean,
    onShowRegisterChange: (Boolean) -> Unit,
    onUserAuthenticated: (User) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader()
        Spacer(modifier = Modifier.height(32.dp))

        if (showRegister) {
            RegisterScreen(
                onRegisterSuccess = { registeredUser ->
                    onUserAuthenticated(registeredUser)
                    onShowRegisterChange(false)
                },
                onBackToLogin = { onShowRegisterChange(false) }
            )
        } else {
            LoginScreen(
                onLoginSuccess = { loggedInUser ->
                    onUserAuthenticated(loggedInUser)
                },
                onNavigateToRegister = { onShowRegisterChange(true) }
            )
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