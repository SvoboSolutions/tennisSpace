package org.tennis.space.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.tennis.space.presentation.auth.login.TennisTextField
import org.tennis.space.presentation.auth.register.RegisterUiState

@Composable
fun RegisterInputFields(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onRegisterSubmit: () -> Unit,
    enabled: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Name Field
        TennisTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = "Vollständiger Name",
            leadingIcon = "👤",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            enabled = enabled,
            isError = uiState.shouldShowNameError,
            supportingText = if (uiState.shouldShowNameError) "Name muss mindestens 2 Zeichen haben" else null
        )

        // Email Field
        TennisTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "E-Mail Adresse",
            leadingIcon = "📧",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            enabled = enabled,
            isError = uiState.shouldShowEmailError,
            supportingText = if (uiState.shouldShowEmailError) "Bitte geben Sie eine gültige E-Mail ein" else null
        )

        // Password Field
        TennisTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = "Passwort",
            leadingIcon = "🔒",
            trailingIcon = if (uiState.isPasswordVisible) "👁️" else "👁️‍🗨️",
            onTrailingIconClick = onPasswordVisibilityToggle,
            visualTransformation = if (uiState.isPasswordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onRegisterSubmit()
                }
            ),
            enabled = enabled,
            isError = uiState.shouldShowPasswordError,
            supportingText = if (uiState.shouldShowPasswordError) "Passwort muss mindestens 6 Zeichen haben" else null
        )
    }
}