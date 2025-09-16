package org.tennis.space.presentation.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tennis.space.presentation.auth.login.FormDivider
import org.tennis.space.presentation.auth.login.TennisErrorMessage
import org.tennis.space.presentation.auth.register.RegisterUiState
import org.tennis.space.presentation.theme.TennisColors

@Composable
fun RegisterForm(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onRegister: () -> Unit,
    onBackToLogin: () -> Unit
) {
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
            RegisterHeader()

            RegisterInputFields(
                uiState = uiState,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onPasswordVisibilityToggle = onPasswordVisibilityToggle,
                onRegisterSubmit = {
                    if (uiState.isFormValid) onRegister()
                },
                enabled = !uiState.isLoading
            )

            if (uiState.errorMessage != null) {
                TennisErrorMessage(message = uiState.errorMessage)
            }

            TennisRegisterButton(
                onRegister = onRegister,
                isLoading = uiState.isLoading,
                enabled = uiState.isFormValid
            )

            FormDivider()

            LoginLink(
                onBackToLogin = onBackToLogin,
                enabled = !uiState.isLoading
            )
        }
    }
}