package com.kiltonik.tiresapp.presentation.auth.login

sealed class LoginViewState{
    object AuthSuccess: LoginViewState()
    object PasswordEmpty: LoginViewState()
    object EmailEmpty: LoginViewState()
}
