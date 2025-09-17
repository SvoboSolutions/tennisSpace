package org.tennis.space.domain.model

import kotlinx.serialization.Serializable
import org.tennis.space.presentation.navigation.NavigationState

@Serializable
data class User(
    val id: String,
    val email: String,
    val name: String,
    val isLoggedIn: Boolean = true,
    val phoneNumber: String? = null,          // Für Kontakt
    val profileImageUrl: String? = null,
    val ownClubs: List<String> = emptyList()
)

fun getNavigationState(user: User): NavigationState {
    return if (user.ownClubs.isEmpty()) {
        NavigationState.NO_CLUB
    } else {
        NavigationState.HAS_CLUB
    }
}


data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

