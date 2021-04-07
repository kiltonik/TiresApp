package com.kiltonik.tiresapp.presentation.main.pressure

import com.kiltonik.tiresapp.App
import com.kiltonik.tiresapp.presentation.base.BaseViewModel

class PressureViewModel: BaseViewModel<PressureAction, PressureViewState>() {

    private val bluetoothRepository = App.appComponent.getBluetoothRepository()

    init {
        postLoading()
        disposable.add(
                bluetoothRepository.getPressureUpdates()
                        .subscribe {
                            postValue(PressureViewState.PressureUpload(
                                    0f,0f,0f,0f
                            ))
                        }
        )
    }

    override fun handleAction(action: PressureAction) {

    }
}