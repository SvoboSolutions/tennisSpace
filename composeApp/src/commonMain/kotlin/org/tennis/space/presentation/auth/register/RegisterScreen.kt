package org.tennis.space.presentation.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.domain.model.RegisterRequest
import org.tennis.space.domain.model.User
import org.tennis.space.presentation.auth.components.*
import org.tennis.space.presentation.auth.login.TennisAppHeader

@Composable
fun RegisterScreen(
    onRegisterSuccess: (User) -> Unit,
    onBackToLogin: () -> Unit
) {
    val authRepository: AuthRepository = koinInject()
    var uiState by remember { mutableStateOf(RegisterUiState()) }
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

            RegisterForm(
                uiState = uiState,
                onNameChange = { uiState = uiState.copy(name = it) },
                onEmailChange = { uiState = uiState.copy(email = it.trim()) },
                onPasswordChange = { uiState = uiState.copy(password = it) },
                onPasswordVisibilityToggle = {
                    uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
                },
                onRegister = {
                    scope.launch {
                        uiState = uiState.copy(isLoading = true, errorMessage = null)

                        val result = authRepository.register(
                            RegisterRequest(
                                email = uiState.email.trim(),
                                password = uiState.password,
                                name = uiState.name.trim()
                            )
                        )

                        result.onSuccess { user ->
                            onRegisterSuccess(user)
                        }.onFailure { exception ->
                            uiState = uiState.copy(
                                errorMessage = exception.message ?: "Registrierung fehlgeschlagen"
                            )
                        }

                        uiState = uiState.copy(isLoading = false)
                    }
                },
                onBackToLogin = onBackToLogin
            )
        }
    }
}

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isValidEmail: Boolean
        get() = email.contains("@") &&
                email.contains(".") &&
                email.length > 5 &&
                !email.startsWith("@") &&
                !email.endsWith("@")

    val isValidPassword: Boolean
        get() = password.length >= 6

    val isValidName: Boolean
        get() = name.trim().length >= 2

    val isFormValid: Boolean
        get() = isValidEmail && isValidPassword && isValidName

    val shouldShowEmailError: Boolean
        get() = email.isNotEmpty() && !isValidEmail

    val shouldShowPasswordError: Boolean
        get() = password.isNotEmpty() && !isValidPassword

    val shouldShowNameError: Boolean
        get() = name.isNotEmpty() && !isValidName
}