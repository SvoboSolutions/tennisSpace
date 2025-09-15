package org.tennis.space.domain.repository

import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.model.ClubMembership
import org.tennis.space.domain.model.CourtBooking

interface ClubRepository {
    suspend fun getAllClubs(): Result<List<TennisClub>>
    suspend fun createClub(club: TennisClub): Result<TennisClub>
    suspend fun getClubById(clubId: String): Result<TennisClub>

    // Membership Management
    suspend fun requestMembership(clubId: String, userId: String): Result<ClubMembership>
    suspend fun getUserMemberships(userId: String): Result<List<ClubMembership>>
    suspend fun getClubMemberships(clubId: String): Result<List<ClubMembership>>

    // Bookings
    suspend fun getClubBookings(clubId: String): Result<List<CourtBooking>>
    suspend fun getUserBookings(userId: String): Result<List<CourtBooking>>
    suspend fun createBooking(booking: CourtBooking): Result<CourtBooking>
    suspend fun cancelBooking(bookingId: String, userId: String): Result<Unit>
}