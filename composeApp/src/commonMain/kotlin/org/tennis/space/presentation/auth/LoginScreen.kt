package org.tennis.space.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.data.repository.AuthRepository
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.User

@Composable
fun LoginScreen(
    onLoginSuccess: (User) -> Unit,
    onNavigateToRegister: () -> Unit = {}
) {
    val authRepository: AuthRepository = koinInject()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LoginCard(
        onLogin = { email, password ->
            scope.launch {
                isLoading = true
                errorMessage = null

                val result = authRepository.login(LoginRequest(email, password))

                result.onSuccess { user ->
                    onLoginSuccess(user)
                }.onFailure { exception ->
                    errorMessage = exception.message ?: "Login fehlgeschlagen"
                }

                isLoading = false
            }
        },
        onNavigateToRegister = onNavigateToRegister,
        isLoading = isLoading,
        errorMessage = errorMessage
    )
}

@Composable
private fun LoginCard(
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    var email by remember { mutableStateOf("test@tennis.com") }
    var password by remember { mutableStateOf("123456") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Anmelden",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-Mail") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Passwort") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            // Error Message
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            LoginButton(
                onLogin = { onLogin(email, password) },
                isLoading = isLoading,
                enabled = email.isNotEmpty() && password.isNotEmpty()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Register Link
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = onNavigateToRegister,
                enabled = !isLoading
            ) {
                Text("Noch kein Konto? Registrieren")
            }
        }
    }
}

@Composable
private fun LoginButton(
    onLogin: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean
) {
    Button(
        onClick = onLogin,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Anmelden...")
        } else {
            Text("Anmelden")
        }
    }
}