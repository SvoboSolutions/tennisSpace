package org.tennis.space.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.User
import org.tennis.space.presentation.auth.components.*

@Composable
fun LoginScreen(
    onLoginSuccess: (User) -> Unit,
    onNavigateToRegister: () -> Unit = {}
) {
    val authRepository: AuthRepository = koinInject()
    var uiState by remember { mutableStateOf(LoginUiState()) }
    val scope = rememberCoroutineScope()

    TennisBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TennisAppHeader()

            Spacer(modifier = Modifier.height(32.dp))

            LoginForm(
                uiState = uiState,
                onEmailChange = { uiState = uiState.copy(email = it) },
                onPasswordChange = { uiState = uiState.copy(password = it) },
                onPasswordVisibilityToggle = {
                    uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
                },
                onLogin = {
                    scope.launch {
                        uiState = uiState.copy(isLoading = true, errorMessage = null)

                        val result = authRepository.login(LoginRequest(uiState.email, uiState.password))

                        result.onSuccess { user ->
                            onLoginSuccess(user)
                        }.onFailure { exception ->
                            uiState = uiState.copy(
                                errorMessage = exception.message ?: "Login fehlgeschlagen"
                            )
                        }

                        uiState = uiState.copy(isLoading = false)
                    }
                },
                onNavigateToRegister = onNavigateToRegister
            )
        }
    }
}

data class LoginUiState(
    val email: String = "test@tennis.com",
    val password: String = "123456",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()
}