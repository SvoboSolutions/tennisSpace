package org.tennis.space.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import org.tennis.space.domain.model.User
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.RegisterRequest

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(
                email = request.email,
                password = request.password
            )

            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = firebaseUser.toUser()
                Result.success(user)
            } else {
                Result.failure(Exception("Login fehlgeschlagen"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(request: RegisterRequest): Result<User> {
        return try {
            println(" Registering user: ${request.email}")
            println(" Password length: ${request.password.length}")

            // Email validation check
            if (!request.email.contains("@") || !request.email.contains(".")) {
                return Result.failure(Exception("E-Mail Format ungültig"))
            }

            val result = firebaseAuth.createUserWithEmailAndPassword(
                email = request.email.trim(), // Whitespace entfernen
                password = request.password
            )

            println(" Registration successful!")

            val firebaseUser = result.user
            if (firebaseUser != null) {
                firebaseUser.updateProfile(displayName = request.name)
                val user = firebaseUser.toUser(request.name)
                Result.success(user)
            } else {
                Result.failure(Exception("Registrierung fehlgeschlagen"))
            }
        } catch (e: Exception) {
            println("Registration failed: ${e.message}")
            Result.failure(Exception("Firebase Fehler: ${e.message}"))
        }
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun FirebaseUser.toUser(displayName: String? = null): User {
        return User(
            id = uid,
            email = email ?: "",
            name = displayName ?: this.displayName ?: "Tennis Player"
        )
    }
}