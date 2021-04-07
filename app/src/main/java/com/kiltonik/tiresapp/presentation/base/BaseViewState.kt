package com.kiltonik.tiresapp.presentation.base

sealed class BaseViewState<out T> {
    data class Error(val throwable: Throwable): BaseViewState<Nothing>()
    data class Message(val message: String): BaseViewState<Nothing>()
    data class Other<out T>(val content: T): BaseViewState<T>()
    object Loading: BaseViewState<Nothing>()
}