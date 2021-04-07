package com.kiltonik.tiresapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.NonNull
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Custom transformer from BroadcastReceiver to Single
 */
fun createSingleFromBroadcastReceiver(
        context: Context,
        intentFilter: IntentFilter
): Observable<Intent> {
    val appContext: Context = context.applicationContext

    return Observable.create { emitter ->
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                emitter.onNext(intent)
            }
        }
        appContext.registerReceiver(receiver, intentFilter)
        emitter.setCancellable { appContext.unregisterReceiver(receiver) }
    }
}



