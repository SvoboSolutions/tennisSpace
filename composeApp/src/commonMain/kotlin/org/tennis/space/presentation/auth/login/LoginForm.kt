package org.tennis.space.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoginHeader()

            LoginInputFields(
                email = uiState.email,
                password = uiState.password,
                isPasswordVisible = uiState.isPasswordVisible,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onPasswordVisibilityToggle = onPasswordVisibilityToggle,
                onLoginSubmit = {
                    keyboardController?.hide()
                    if (uiState.isFormValid) onLogin()
                },
                enabled = !uiState.isLoading
            )

            if (uiState.errorMessage != null) {
                TennisErrorMessage(message = uiState.errorMessage)
            }

            TennisLoginButton(
                onLogin = onLogin,
                isLoading = uiState.isLoading,
                enabled = uiState.isFormValid
            )

            FormDivider()

            RegisterLink(
                onNavigateToRegister = onNavigateToRegister,
                enabled = !uiState.isLoading
            )
        }
    }
}