package org.tennis.space.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.User
import androidx.compose.runtime.rememberCoroutineScope
import org.tennis.space.data.repository.AuthRepository

@Composable
fun MainScreen(
    user: User,
    onLogout: () -> Unit
) {
    val authRepository: AuthRepository = koinInject()
    val scope = rememberCoroutineScope()

    UserCard(
        user = user,
        onLogout = {
            scope.launch {
                authRepository.logout()
                onLogout()
            }
        }
    )
}

@Composable
private fun UserCard(
    user: User,
    onLogout: () -> Unit
) {
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
                text = "Willkommen! 👋",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Navigate to club search */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🎾 Tennisvereine suchen")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Abmelden")
            }
        }
    }
}