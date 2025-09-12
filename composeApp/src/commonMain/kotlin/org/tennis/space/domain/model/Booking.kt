package org.tennis.space.domain.model


import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class CourtBooking @OptIn(ExperimentalTime::class) constructor(
    val id: String = "",
    val courtId: String,
    val clubId: String,
    val bookedBy: String,                     // User ID
    val players: List<String> = emptyList(),  // Weitere Spieler User IDs

    val startTime: Long,                      // Timestamp
    val endTime: Long,

    val status: BookingStatus = BookingStatus.CONFIRMED,
    val bookingType: BookingType = BookingType.REGULAR,

    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val createdBy: String,                    // User oder Admin ID

    // Für Admin-Features
    val notes: String? = null,
    val cancelledAt: Long? = null,
    val cancelledBy: String? = null,
    val cancellationReason: String? = null
)

enum class BookingStatus {
    CONFIRMED,
    CANCELLED,
    BLOCKED       // Admin-Sperrung
}

enum class BookingType {
    REGULAR,      // Normale User-Buchung
    TRAINING,     // Admin-Training (wiederkehrend)
    MAINTENANCE,  // Wartung/Sperrung
    TOURNAMENT    // Turnier
}