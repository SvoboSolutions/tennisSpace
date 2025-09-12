package org.tennis.space

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform