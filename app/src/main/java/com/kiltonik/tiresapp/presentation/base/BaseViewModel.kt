package com.kiltonik.tiresapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<Action, ViewState>(): ViewModel() {

    private val nextAction : MutableLiveData<Action> = MutableLiveData()

    protected val disposable = CompositeDisposable()

    val publicViewState: LiveData<BaseViewState<ViewState>>
        get() = viewState

    private val viewState : MutableLiveData<BaseViewState<ViewState>> = MutableLiveData()

    init {
        nextAction.observeForever {
            handleAction(it)
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    abstract fun handleAction(action: Action)

    fun dispatch(action: Action) {
        nextAction.value = action!!
    }

    fun postValue(state: ViewState) = viewState.postValue(BaseViewState.Other(state))

    fun postError(throwable: Throwable) = viewState.postValue(BaseViewState.Error(throwable))

    fun postMessage(message: String) = viewState.postValue(BaseViewState.Message(message))

    fun postLoading() = viewState.postValue(BaseViewState.Loading)
}