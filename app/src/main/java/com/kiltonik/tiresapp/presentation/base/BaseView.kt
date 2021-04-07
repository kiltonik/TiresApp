package com.kiltonik.tiresapp.presentation.base

interface BaseView<ViewState> {
    fun render(viewState: ViewState)
}