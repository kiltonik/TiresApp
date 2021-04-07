package com.kiltonik.tiresapp.presentation.auth.registration

sealed class RegistrationViewState{
    object RegistrationSuccessful: RegistrationViewState()
    object PasswordEmpty: RegistrationViewState()
    object EmailEmpty: RegistrationViewState()
    object PasswordsAreNotEqual: RegistrationViewState()
}
