package org.tennis.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.tennis.space.data.repository.AuthRepository
import org.tennis.space.domain.model.User
import org.tennis.space.presentation.auth.LoginScreen
import org.tennis.space.presentation.auth.RegisterScreen
import org.tennis.space.presentation.main.MainScreen
import org.tennis.space.presentation.theme.TennisSpaceTheme

@Composable
fun TennisApp() {
    val authRepository: AuthRepository = koinInject()
    var user by remember { mutableStateOf<User?>(null) }
    var showRegister by remember { mutableStateOf(false) }

    // Check current user on startup
    LaunchedEffect(Unit) {
        user = authRepository.getCurrentUser()
    }

    TennisSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppHeader()
                Spacer(modifier = Modifier.height(32.dp))

                when {
                    user != null -> {
                        MainScreen(
                            user = user!!,
                            onLogout = { user = null }
                        )
                    }
                    showRegister -> {
                        RegisterScreen(
                            onRegisterSuccess = { registeredUser ->
                                user = registeredUser
                                showRegister = false
                            },
                            onBackToLogin = { showRegister = false }
                        )
                    }
                    else -> {
                        LoginScreen(
                            onLoginSuccess = { loggedInUser ->
                                user = loggedInUser
                            },
                            onNavigateToRegister = { showRegister = true }
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
            text = "🎾 Tennis Space",
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