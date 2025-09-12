package org.tennis.space.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val isLoggedIn: Boolean = true
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