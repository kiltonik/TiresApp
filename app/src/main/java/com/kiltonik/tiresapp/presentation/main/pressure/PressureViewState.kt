package com.kiltonik.tiresapp.presentation.main.pressure

import android.content.BroadcastReceiver

sealed class PressureViewState {

    data class PressureUpload(
            val first: Float,
            val second: Float,
            val third: Float,
            val fourth: Float
    ): PressureViewState()

    data class EstablishingConnection(
            val broadcastReceiver: BroadcastReceiver
    ): PressureViewState()



}