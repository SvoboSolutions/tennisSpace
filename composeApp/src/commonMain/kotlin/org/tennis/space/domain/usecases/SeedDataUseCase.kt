package org.tennis.space.domain.usecases

import org.tennis.space.domain.model.BookingSettings
import org.tennis.space.domain.model.CourtSurface
import org.tennis.space.domain.model.OperatingHours
import org.tennis.space.domain.model.TennisClub
import org.tennis.space.domain.model.TennisCourt
import org.tennis.space.domain.repository.ClubRepository

class SeedDataUseCase(
    private val clubRepository: ClubRepository
) {
    suspend fun seedClubs(): Result<List<String>> {
        return try {
            val clubs = createTestClubs()
            val createdClubIds = mutableListOf<String>()

            clubs.forEach { club ->
                println("Creating club: ${club.name}")
                val result = clubRepository.createClub(club)
                result.onSuccess { createdClub ->
                    createdClubIds.add(createdClub.id)
                    println("Created club: ${createdClub.name} with ID: ${createdClub.id}")
                }.onFailure { error ->
                    println("Failed to create club ${club.name}: ${error.message}")
                }
            }

            Result.success(createdClubIds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createTestClubs(): List<TennisClub> {
        return listOf(
            TennisClub(
                name = "TC Rot-Weiß Aachen",
                address = "Krefelder Straße 223, 52070 Aachen",
                description = "Traditioneller Tennisclub mit 4 modernen Sandplätzen und Flutlicht",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Center Court",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Platz 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Platz 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Platz 4",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    )
                ),
                bookingSettings = BookingSettings(
                    maxAdvanceBookingDays = 7,
                    maxHoursPerUserPerDay = 2,
                    allowCancellation = true,
                    cancellationDeadlineHours = 2
                ),
                operatingHours = OperatingHours(
                    openTime = "08:00",
                    closeTime = "22:00",
                    daysOfWeek = listOf(1, 2, 3, 4, 5, 6, 7)
                )
            ),

            TennisClub(
                name = "Alemannia Aachen Tennis",
                address = "Krefelder Straße 199, 52070 Aachen",
                description = "Sportverein mit 5 Plätzen und Indoor-Halle",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Außenplatz 1",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Außenplatz 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Außenplatz 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Halle 1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Halle 2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "TC Blau-Weiß Aachen",
                address = "Monschauer Straße 45, 52064 Aachen",
                description = "Moderner Club mit 6 Hartplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Court 1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = 25.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Court 2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = 25.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Court 3",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Court 4",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Court 5",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court6",
                        name = "Court 6",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "Tennisclub Eschweiler",
                address = "Dürener Straße 12, 52249 Eschweiler",
                description = "Gemütlicher Verein mit 3 Sandplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Platz A",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Platz B",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Platz C",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "TC Herzogenrath",
                address = "Hauptstraße 88, 52134 Herzogenrath",
                description = "Premium Club mit Kunstrasenplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Premium Court 1",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Premium Court 2",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Court 3",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Court 4",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Court 5",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "Tennispark Stolberg",
                address = "Rathausstraße 100, 52222 Stolberg",
                description = "Großer Tennispark mit 8 Plätzen verschiedener Beläge",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Center Court",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Sandplatz 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Sandplatz 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Sandplatz 4",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Hartplatz 1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court6",
                        name = "Hartplatz 2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court7",
                        name = "Indoor 1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court8",
                        name = "Indoor 2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "TC Würselen",
                address = "Kaiserstraße 77, 52146 Würselen",
                description = "Familiärer Verein mit 4 Sandplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Platz 1",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Platz 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Platz 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Platz 4",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "Tennishalle Alsdorf",
                address = "Annastraße 50, 52477 Alsdorf",
                description = "Indoor-Zentrum mit 6 Hallenplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Halle A1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 30.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Halle A2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 30.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Halle B1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 25.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Halle B2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 25.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Halle C1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 25.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court6",
                        name = "Halle C2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 25.0,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "TC Baesweiler",
                address = "Sportstraße 15, 52499 Baesweiler",
                description = "Vereinsanlage mit 5 Außenplätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Hauptplatz",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Platz 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Platz 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Nebenplatz 1",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Nebenplatz 2",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    )
                )
            ),

            TennisClub(
                name = "Grün-Weiß Düren Tennis",
                address = "Valencienner Straße 89, 52349 Düren",
                description = "Großverein mit 7 verschiedenen Plätzen",
                courts = listOf(
                    TennisCourt(
                        id = "court1",
                        name = "Center Court",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court2",
                        name = "Sand 2",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court3",
                        name = "Sand 3",
                        surface = CourtSurface.CLAY,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court4",
                        name = "Hart 1",
                        surface = CourtSurface.HARD,
                        hasFloodlights = true,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court5",
                        name = "Hart 2",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = 20.0,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court6",
                        name = "Kunstrasen",
                        surface = CourtSurface.ARTIFICIAL,
                        hasFloodlights = false,
                        isIndoor = false,
                        pricePerHour = null,
                        isActive = true
                    ),
                    TennisCourt(
                        id = "court7",
                        name = "Indoor",
                        surface = CourtSurface.HARD,
                        hasFloodlights = false,
                        isIndoor = true,
                        pricePerHour = 35.0,
                        isActive = true
                    )
                )
            )
        )
    }
}