package com.kiltonik.tiresapp.di

import com.kiltonik.tiresapp.domain.EnterInteractor
import com.kiltonik.tiresapp.repository.EnterRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Singleton
    @Provides
    fun provideEnterInteractor(enterRepository: EnterRepository) = EnterInteractor(enterRepository)
}