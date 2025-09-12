package org.tennis.space.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TennisClub(
    val id: String = "",
    val name: String,
    val address: String,
    val description: String,
    val imageUrl: String? = null,

    // Buchungseinstellungen (vom Admin konfiguriert)
    val bookingSettings: BookingSettings = BookingSettings(),
    val operatingHours: OperatingHours = OperatingHours(),

    val courts: List<TennisCourt> = emptyList(),
    val adminIds: List<String> = emptyList()
)

@Serializable
data class BookingSettings(
    val maxAdvanceBookingDays: Int = 7,       // Wie weit im Voraus buchbar
    val maxHoursPerUserPerDay: Int = 2,       // Booking-Limit pro User
    val allowCancellation: Boolean = true,
    val cancellationDeadlineHours: Int = 2    // Storno-Frist
)

@Serializable
data class OperatingHours(
    val openTime: String = "08:00",           // "HH:mm" Format
    val closeTime: String = "22:00",
    val daysOfWeek: List<Int> = listOf(1,2,3,4,5,6,7) // 1=Monday, 7=Sunday
)

@Serializable
data class TennisCourt(
    val id: String = "",
    val name: String,                         // "Platz 1", "Center Court"
    val surface: CourtSurface,
    val hasFloodlights: Boolean = false,      // Wichtig für Abendstunden
    val isIndoor: Boolean = false,
    val pricePerHour: Double? = null,         // Null = kostenlos für Mitglieder
    val isActive: Boolean = true              // Für dauerhafte Sperrungen
)

enum class CourtSurface {
    CLAY, HARD, GRASS, ARTIFICIAL
}