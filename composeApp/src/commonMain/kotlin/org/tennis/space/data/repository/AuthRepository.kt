package org.tennis.space.data.repository

import org.tennis.space.domain.model.User
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.RegisterRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<User>
    suspend fun register(request: RegisterRequest): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
    fun isLoggedIn(): Boolean
}