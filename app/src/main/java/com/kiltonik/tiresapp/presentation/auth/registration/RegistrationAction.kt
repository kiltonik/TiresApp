package com.kiltonik.tiresapp.presentation.auth.registration

import android.text.Editable

sealed class RegistrationAction{
    data class RegisterClicked(
        val email: Editable?,
        val password: Editable?,
        val passwordConfirmed: Editable?
    ) : RegistrationAction()
}
