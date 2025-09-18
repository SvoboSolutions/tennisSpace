package org.tennis.space.domain.repository

import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.RegisterRequest
import org.tennis.space.domain.model.User

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<User>
    suspend fun register(request: RegisterRequest): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
    suspend fun joinClub(userId: String, clubId: String): Result<User>
    suspend fun updateUser(user: User): Result<User>
    fun isLoggedIn(): Boolean
}