package com.kiltonik.tiresapp.di

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val context: Context) {

    @Singleton
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideAuth() = Firebase.auth

}