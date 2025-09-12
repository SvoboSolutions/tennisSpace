package org.tennis.space.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.dsl.module
import org.tennis.space.data.repository.FirebaseAuthRepository
import org.tennis.space.data.repository.AuthRepository

val appModule = module {
    // Firebase
    single { Firebase.auth }

    // Repository
    single<AuthRepository> { FirebaseAuthRepository(get()) }
}