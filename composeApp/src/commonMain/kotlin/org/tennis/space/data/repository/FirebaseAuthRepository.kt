package org.tennis.space.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FirebaseFirestore
import org.tennis.space.domain.model.LoginRequest
import org.tennis.space.domain.model.RegisterRequest
import org.tennis.space.domain.model.User
import org.tennis.space.domain.repository.AuthRepository

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(
                email = request.email,
                password = request.password
            )

            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = loadUserFromFirestore(firebaseUser.uid)
                    ?: firebaseUser.toUser()
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
            if (!request.email.contains("@") || !request.email.contains(".")) {
                return Result.failure(Exception("E-Mail Format ungültig"))
            }

            val result = firebaseAuth.createUserWithEmailAndPassword(
                email = request.email.trim(),
                password = request.password
            )

            val firebaseUser = result.user
            if (firebaseUser != null) {
                firebaseUser.updateProfile(displayName = request.name)

                val user = User(
                    id = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = request.name,
                    ownClubs = emptyList()
                )

                // Save user to Firestore
                saveUserToFirestore(user)

                Result.success(user)
            } else {
                Result.failure(Exception("Registrierung fehlgeschlagen"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Firebase Fehler: ${e.message}"))
        }
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.currentUser ?: return null
        return loadUserFromFirestore(firebaseUser.uid) ?: firebaseUser.toUser()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun joinClub(userId: String, clubId: String): Result<User> {
        return try {
            val currentUser = getCurrentUser()
            if (currentUser == null) {
                return Result.failure(Exception("User not logged in"))
            }

            if (currentUser.ownClubs.contains(clubId)) {
                return Result.success(currentUser)
            }

            val updatedUser = currentUser.copy(ownClubs = currentUser.ownClubs + clubId)

            // Update in Firestore
            firestore.collection("users")
                .document(userId)
                .update(mapOf("ownClubs" to updatedUser.ownClubs))

            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            saveUserToFirestore(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private suspend fun loadUserFromFirestore(uid: String): User? {
        return try {
            val document = firestore.collection("users").document(uid).get()
            if (document.exists) {
                val data: Map<String, Any> = document.data()
                User(
                    id = data["id"] as? String ?: uid,
                    email = data["email"] as? String ?: "",
                    name = data["name"] as? String ?: "",
                    isLoggedIn = data["isLoggedIn"] as? Boolean ?: true,
                    phoneNumber = data["phoneNumber"] as? String,
                    profileImageUrl = data["profileImageUrl"] as? String,
                    ownClubs = (data["ownClubs"] as? List<String>) ?: emptyList()
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users")
            .document(user.id)
            .set(mapOf(
                "id" to user.id,
                "email" to user.email,
                "name" to user.name,
                "isLoggedIn" to user.isLoggedIn,
                "phoneNumber" to user.phoneNumber,
                "profileImageUrl" to user.profileImageUrl,
                "ownClubs" to user.ownClubs
            ))
    }

    private fun FirebaseUser.toUser(displayName: String? = null): User {
        return User(
            id = uid,
            email = email ?: "",
            name = displayName ?: this.displayName ?: "Tennis Player",
            ownClubs = emptyList()
        )
    }
}