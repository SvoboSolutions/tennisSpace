package org.tennis.space.presentation.auth.register

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
import org.tennis.space.domain.model.RegisterRequest
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.presentation.theme.TennisDimensions

@Composable
fun RegisterScreen(
    onRegisterSuccess: (User) -> Unit,
    onBackToLogin: () -> Unit
) {
    val authRepository: AuthRepository = koinInject()
    var uiState by remember { mutableStateOf(RegisterUiState()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TennisDimensions.SpaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RegisterForm(
            uiState = uiState,
            onNameChange = { uiState = uiState.copy(name = it) },
            onEmailChange = { uiState = uiState.copy(email = it.trim()) },
            onPasswordChange = { uiState = uiState.copy(password = it) },
            onRegister = {
                scope.launch {
                    uiState = uiState.copy(isLoading = true, errorMessage = null)

                    authRepository.register(
                        RegisterRequest(
                            email = uiState.email.trim(),
                            password = uiState.password,
                            name = uiState.name.trim()
                        )
                    ).onSuccess { onRegisterSuccess(it) }
                        .onFailure { uiState = uiState.copy(errorMessage = it.message) }

                    uiState = uiState.copy(isLoading = false)
                }
            },
            onBackToLogin = onBackToLogin
        )
    }
}

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isValidEmail: Boolean
        get() = email.contains("@") && email.contains(".") && email.length > 5

    val isFormValid: Boolean
        get() = name.trim().length >= 2 && isValidEmail && password.length >= 6
}