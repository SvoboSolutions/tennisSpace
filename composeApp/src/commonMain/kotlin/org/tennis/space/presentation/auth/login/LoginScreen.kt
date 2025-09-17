package org.tennis.space.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.presentation.theme.TennisDimensions

@Composable
fun LoginScreen(
    onLoginSuccess: (User) -> Unit,
    onNavigateToRegister: () -> Unit = {}
) {
    val authRepository: AuthRepository = koinInject()
    var uiState by remember { mutableStateOf(LoginUiState()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TennisDimensions.SpaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginForm(
            uiState = uiState,
            onEmailChange = { uiState = uiState.copy(email = it) },
            onPasswordChange = { uiState = uiState.copy(password = it) },
            onLogin = {
                scope.launch {
                    uiState = uiState.copy(isLoading = true, errorMessage = null)

                    authRepository.login(LoginRequest(uiState.email, uiState.password))
                        .onSuccess { onLoginSuccess(it) }
                        .onFailure { uiState = uiState.copy(errorMessage = it.message) }

                    uiState = uiState.copy(isLoading = false)
                }
            },
            onNavigateToRegister = onNavigateToRegister,
        )
    }
}

data class LoginUiState(
    val email: String = "test@tennis.com",
    val password: String = "123456",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean get() = email.isNotEmpty() && password.length >= 6
}