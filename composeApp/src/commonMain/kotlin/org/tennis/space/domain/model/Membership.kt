package org.tennis.space.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class ClubMembership @OptIn(ExperimentalTime::class) constructor(
    val id: String = "",
    val userId: String,
    val clubId: String,
    val status: MembershipStatus = MembershipStatus.PENDING,
    val joinRequestDate: Long = Clock.System.now().toEpochMilliseconds(),
    val approvedDate: Long? = null,
    val approvedBy: String? = null, // Admin User ID
    val membershipType: MembershipType = MembershipType.FULL
)

enum class MembershipStatus {
    PENDING,    // Wartet auf Admin-Bestätigung
    APPROVED,   // Aktives Mitglied
    REJECTED,   // Abgelehnt
    SUSPENDED   // Gesperrt
}

enum class MembershipType {
    FULL        // Später: GUEST, TRIAL, etc.
}