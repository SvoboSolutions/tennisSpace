package org.tennis.space.data.repository

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import org.tennis.space.domain.model.ClubMembership
import org.tennis.space.domain.model.CourtBooking
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.repository.ClubRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class FirebaseClubRepository(
    private val firestore: FirebaseFirestore
) : ClubRepository {

    companion object {
        private const val CLUBS_COLLECTION = "clubs"
        private const val MEMBERSHIPS_COLLECTION = "memberships"
        private const val BOOKINGS_COLLECTION = "bookings"
    }

    override suspend fun getAllClubs(): Result<List<TennisClub>> {
        return try {
            val snapshot = firestore.collection(CLUBS_COLLECTION).get()
            val clubs = snapshot.documents.map { doc ->
                doc.data<TennisClub>().copy(id = doc.id)
            }
            Result.success(clubs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createClub(club: TennisClub): Result<TennisClub> {
        return try {
            val docRef = firestore.collection(CLUBS_COLLECTION).add(club)
            val createdClub = club.copy(id = docRef.id)
            Result.success(createdClub)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClubById(clubId: String): Result<TennisClub> {
        return try {
            val doc = firestore.collection(CLUBS_COLLECTION).document(clubId).get()
            if (doc.exists) {
                val club = doc.data<TennisClub>().copy(id = doc.id)
                Result.success(club)
            } else {
                Result.failure(Exception("Club nicht gefunden"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    @OptIn(ExperimentalTime::class)
    override suspend fun requestMembership(clubId: String, userId: String): Result<ClubMembership> {
        return try {
            val membership = ClubMembership(
                userId = userId,
                clubId = clubId,
                joinRequestDate = Clock.System.now().toEpochMilliseconds()
            )

            val docRef = firestore.collection(MEMBERSHIPS_COLLECTION).add(membership)
            val createdMembership = membership.copy(id = docRef.id)
            Result.success(createdMembership)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserMemberships(userId: String): Result<List<ClubMembership>> {
        return try {
            val snapshot = firestore.collection(MEMBERSHIPS_COLLECTION)
                .where("userId", "==", userId)
                .get()

            val memberships = snapshot.documents.map { doc ->
                doc.data<ClubMembership>().copy(id = doc.id)
            }
            Result.success(memberships)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClubMemberships(clubId: String): Result<List<ClubMembership>> {
        return try {
            val snapshot = firestore.collection(MEMBERSHIPS_COLLECTION)
                .where("clubId", "==", clubId)
                .get()

            val memberships = snapshot.documents.map { doc ->
                doc.data<ClubMembership>().copy(id = doc.id)
            }
            Result.success(memberships)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClubBookings(clubId: String): Result<List<CourtBooking>> {
        return try {
            val snapshot = firestore.collection(BOOKINGS_COLLECTION)
                .where { "clubId" equalTo clubId }
                .get()

            val bookings = snapshot.documents.map { doc ->
                doc.data<CourtBooking>().copy(id = doc.id)
            }
            Result.success(bookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserBookings(userId: String): Result<List<CourtBooking>> {
        return try {
            val snapshot = firestore.collection(BOOKINGS_COLLECTION)
                .where { "bookedBy" equalTo userId }
                .get()

            val bookings = snapshot.documents.map { doc ->
                doc.data<CourtBooking>().copy(id = doc.id)
            }
            Result.success(bookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createBooking(booking: CourtBooking): Result<CourtBooking> {
        return try {
            val docRef = firestore.collection(BOOKINGS_COLLECTION).add(booking)
            val createdBooking = booking.copy(id = docRef.id)
            Result.success(createdBooking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun cancelBooking(bookingId: String, userId: String): Result<Unit> {
        return try {
            firestore.collection(BOOKINGS_COLLECTION)
                .document(bookingId)
                .update(
                    "status" to "CANCELLED",
                    "cancelledAt" to Clock.System.now().toEpochMilliseconds(),
                    "cancelledBy" to userId
                )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}