package com.kiltonik.tiresapp.di

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.kiltonik.tiresapp.repository.BluetoothRepository
import com.kiltonik.tiresapp.repository.EnterRepository
import com.kiltonik.tiresapp.repository.PressureRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideEnterRepository(auth: FirebaseAuth) = EnterRepository(auth)

    @Singleton
    @Provides
    fun provideBluetoothRepository(
            context: Context,
            bluetoothAdapter: BluetoothAdapter) = BluetoothRepository(bluetoothAdapter, context)

    @Singleton
    @Provides
    fun provideBluetoothAdapter() = BluetoothAdapter.getDefaultAdapter()

    @Singleton
    @Provides
    fun providePressureRepository() = PressureRepository()
}