package com.kiltonik.tiresapp.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import com.kiltonik.tiresapp.createSingleFromBroadcastReceiver
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class BluetoothRepository @Inject constructor(
        private val bluetoothAdapter: BluetoothAdapter,
        private val context: Context
) {


    lateinit var socket: BluetoothSocket

    private val bufferStore = ByteArray(1024)

    fun getPressureUpdates(): Flowable<Int> =
            findDevice()
                    .doOnSubscribe {
                        startDiscovery()
                    }
                    .flatMap(this@BluetoothRepository::connectDevice)
                    .toFlowable()
                    .flatMap(this@BluetoothRepository::workWithData)
                    .doOnCancel { closeConnection() }
                    .doOnTerminate { closeConnection() }
                    .doOnComplete { closeConnection() }

    private fun scanNewDevices() = createSingleFromBroadcastReceiver(context, IntentFilter(BluetoothDevice.ACTION_FOUND))
            .map { it.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as? BluetoothDevice }
            .filter { it.address == SENSOR_MAC_ADDRESS }

    private fun findDevice(): Single<BluetoothDevice?> =
        Observable.fromIterable(bluetoothAdapter.bondedDevices)
                .filter {
                    it.address == SENSOR_MAC_ADDRESS
                }
                .switchIfEmpty(scanNewDevices())
                .singleOrError()

    private fun startDiscovery(){
        bluetoothAdapter.startDiscovery()
    }

    private fun connectDevice(device: BluetoothDevice): Single<BluetoothSocket> =
            Single.fromCallable {
                device.createRfcommSocketToServiceRecord(APP_UUID)
            }
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        bluetoothAdapter.cancelDiscovery()
                    }
                    .map {
                        it.also {
                            it.connect()
                            socket = it
                        }
                    }
                    .observeOn(AndroidSchedulers.mainThread())

    private fun workWithData(socket: BluetoothSocket) =
            Single.fromCallable {
                socket.inputStream.read(bufferStore)
            }
                    .repeat()

    private fun closeConnection() {
        if(this::socket.isInitialized && socket.isConnected)
            socket.close()
    }

    companion object{
        private const val SENSOR_MAC_ADDRESS: String =  ""
        private val APP_UUID = UUID.fromString("0595ee03-c926-4861-a2e9-59c235057625")
    }
}
