package com.kiltonik.tiresapp.presentation.auth.login

import android.text.Editable
import com.kiltonik.tiresapp.App
import com.kiltonik.tiresapp.presentation.base.BaseViewModel

class LoginViewModel : BaseViewModel<LoginAction, LoginViewState>(){

    var interactor = App.appComponent.getEnterInteractor()

    override fun handleAction(action: LoginAction) {
        when(action){
            is LoginAction.LogInClicked -> handleLogin(action.email, action.password)
        }
    }

    private fun handleLogin(email: Editable?, password: Editable?){
        when {
            email.isNullOrEmpty() ->
                postValue(LoginViewState.EmailEmpty)

            password.isNullOrEmpty() ->
                postValue(LoginViewState.PasswordEmpty)

            else -> {
                postLoading()
                interactor.loginUser(
                    email = email.toString(),
                    password = password.toString()
                ).addOnCompleteListener {
                    if(it.isSuccessful) postValue(LoginViewState.AuthSuccess)
                    else
                        postMessage(
                            "Упс... Что-то пошло не так попробуйте позднее")
                }
            }
        }
    }
}