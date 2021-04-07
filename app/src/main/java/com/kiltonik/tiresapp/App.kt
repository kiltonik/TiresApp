package com.kiltonik.tiresapp

import android.app.Application
import com.kiltonik.tiresapp.di.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}