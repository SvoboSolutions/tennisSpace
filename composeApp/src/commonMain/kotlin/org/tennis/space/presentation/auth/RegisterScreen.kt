package org.tennis.space.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.domain.model.RegisterRequest
import org.tennis.space.domain.model.User


@Composable
fun RegisterScreen(
    onRegisterSuccess: (User) -> Unit,
    onBackToLogin: () -> Unit
) {
    val authRepository: AuthRepository = koinInject()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    RegisterCard(
        onRegister = { email, password, name ->
            scope.launch {
                isLoading = true
                errorMessage = null

                val result = authRepository.register(RegisterRequest(email, password, name))

                result.onSuccess { user ->
                    onRegisterSuccess(user)
                }.onFailure { exception ->
                    errorMessage = exception.message ?: "Registrierung fehlgeschlagen"
                }

                isLoading = false
            }
        },
        onBackToLogin = onBackToLogin,
        isLoading = isLoading,
        errorMessage = errorMessage
    )
}

@Composable
private fun RegisterCard(
    onRegister: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    // Email Validation
    val isValidEmail = email.contains("@") &&
            email.contains(".") &&
            email.length > 5 &&
            !email.startsWith("@") &&
            !email.endsWith("@")

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
                text = "Registrieren",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() }, // Whitespace entfernen
                label = { Text("E-Mail") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = email.isNotEmpty() && !isValidEmail,
                supportingText = {
                    if (email.isNotEmpty() && !isValidEmail) {
                        Text(
                            text = "Ungültige E-Mail Adresse",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
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

            // Register Button
            RegisterButton(
                onRegister = { onRegister(email.trim(), password, name.trim()) },
                isLoading = isLoading,
                enabled = isValidEmail && password.length >= 6 && name.trim().isNotEmpty()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Back to Login
            TextButton(
                onClick = onBackToLogin,
                enabled = !isLoading
            ) {
                Text("Schon ein Konto? Anmelden")
            }
        }
    }
}

@Composable
private fun RegisterButton(
    onRegister: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean
) {
    Button(
        onClick = onRegister,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrieren...")
        } else {
            Text("Registrieren")
        }
    }
}