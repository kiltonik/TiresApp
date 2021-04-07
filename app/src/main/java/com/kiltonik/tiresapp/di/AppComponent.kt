package com.kiltonik.tiresapp.di

import com.kiltonik.tiresapp.domain.EnterInteractor
import com.kiltonik.tiresapp.presentation.main.MainActivity
import com.kiltonik.tiresapp.repository.BluetoothRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    RepositoryModule::class,
    InteractorModule::class
])
@Singleton
interface AppComponent {
    fun inject(target: MainActivity)
//
//    fun inject(target: RegistrationFragment)
//
//    fun inject(target: LogInFragment)
//
    fun getEnterInteractor(): EnterInteractor

    fun getBluetoothRepository(): BluetoothRepository

}
