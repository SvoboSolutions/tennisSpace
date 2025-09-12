package org.tennis.space.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val name: String,
    val isLoggedIn: Boolean = true,
    val phoneNumber: String? = null,          // Für Kontakt
    val profileImageUrl: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)