package org.tennis.space.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module
import org.tennis.space.data.repository.FirebaseAuthRepository
import org.tennis.space.data.repository.FirebaseClubRepository
import org.tennis.space.domain.repository.AuthRepository
import org.tennis.space.domain.repository.ClubRepository
import org.tennis.space.domain.usecases.SeedDataUseCase

val appModule = module {
    // Firebase
    single { Firebase.auth }
    single { Firebase.firestore }

    // Repositories
    single<AuthRepository> { FirebaseAuthRepository(get()) }
    single<ClubRepository> { FirebaseClubRepository(get()) }
    single { SeedDataUseCase(get()) }
}