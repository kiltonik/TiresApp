package com.kiltonik.tiresapp.presentation.auth.login

import android.text.Editable
import com.google.firebase.auth.PhoneAuthOptions

sealed class LoginAction{
    data class LogInClicked(
        val email: Editable?,
        val password: Editable?
        ) : LoginAction()
}
