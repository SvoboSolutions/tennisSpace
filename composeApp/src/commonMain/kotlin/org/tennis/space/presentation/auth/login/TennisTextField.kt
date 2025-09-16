package org.tennis.space.presentation.auth.login

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.tennis.space.presentation.theme.TennisColors

@Composable
fun TennisTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: String,
    modifier: Modifier = Modifier,
    trailingIcon: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = when {
                    !enabled -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    isError -> TennisColors.Error
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        },
        leadingIcon = {
            Text(
                text = leadingIcon,
                style = MaterialTheme.typography.titleMedium
            )
        },
        trailingIcon = {
            trailingIcon?.let { icon ->
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Text(
                            text = icon,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        },
        supportingText = {
            supportingText?.let { text ->
                Text(
                    text = text,
                    color = if (isError) TennisColors.Error else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled,
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = if (isError) TennisColors.Error else TennisColors.ForestGreen,
            unfocusedIndicatorColor = if (isError) TennisColors.Error else MaterialTheme.colorScheme.outline,
            disabledIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            errorIndicatorColor = TennisColors.Error,
            cursorColor = TennisColors.ForestGreen,
            focusedLeadingIconColor = if (isError) TennisColors.Error else TennisColors.ForestGreen,
            focusedLabelColor = if (isError) TennisColors.Error else TennisColors.ForestGreen
        )
    )
}