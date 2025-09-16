package org.tennis.space.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.presentation.shared.ActionButtons
import org.tennis.space.presentation.theme.TennisDimensions

@Composable
fun MainScreen(
    user: User,
    onLogout: () -> Unit,
    onSearchClubs: () -> Unit
) {
    val authRepository: AuthRepository = koinInject()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TennisDimensions.SpaceLarge), // Mehr Padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TennisDimensions.SpaceXXLarge) // Mehr Abstand
        ) {
            WelcomeHeader(user = user)

            ActionButtons(
                onSearchClubs = onSearchClubs,
                onLogout = {
                    scope.launch {
                        authRepository.logout()
                        onLogout()
                    }
                }
            )
        }
    }
}