package com.kiltonik.tiresapp.presentation.auth.registration

import com.kiltonik.tiresapp.App
import com.kiltonik.tiresapp.di.AppComponent
import com.kiltonik.tiresapp.di.DaggerAppComponent
import com.kiltonik.tiresapp.domain.EnterInteractor
import com.kiltonik.tiresapp.presentation.auth.login.LoginViewState
import com.kiltonik.tiresapp.presentation.base.BaseViewModel
import com.kiltonik.tiresapp.presentation.base.BaseViewState
import javax.inject.Inject

class RegistrationViewModel() : BaseViewModel<RegistrationAction, RegistrationViewState>(){

    var interactor = App.appComponent.getEnterInteractor()

    override fun handleAction(action: RegistrationAction) {
        when(action){
            is RegistrationAction.RegisterClicked -> {
                registerClicked(action)
            }
        }
    }

    private fun registerClicked(action: RegistrationAction.RegisterClicked) {
        when {
            action.email.isNullOrEmpty() ->
                postValue(RegistrationViewState.EmailEmpty)

            action.password.isNullOrEmpty() ->
                postValue(RegistrationViewState.PasswordEmpty)

            action.passwordConfirmed?.toString()?.equals(action.password.toString()) != true ->
                postValue(RegistrationViewState.PasswordsAreNotEqual)

            else -> {
                postLoading()

                interactor.registerUser(
                    email = action.email.toString(),
                    password = action.password.toString()
                ).addOnCompleteListener {

                    if(it.isSuccessful) postValue(RegistrationViewState.RegistrationSuccessful)
                    else
                        postMessage(
                                it.exception?.localizedMessage ?: "Упс... Что-то пошло не так"
                            )
                }
            }
        }
    }
}