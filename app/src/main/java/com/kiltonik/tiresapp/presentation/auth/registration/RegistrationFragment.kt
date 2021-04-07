package com.kiltonik.tiresapp.presentation.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.kiltonik.tiresapp.R
import com.kiltonik.tiresapp.databinding.FragmentRegisterBinding
import com.kiltonik.tiresapp.presentation.auth.login.LoginViewState
import com.kiltonik.tiresapp.presentation.base.BaseFragment

class RegistrationFragment:
    BaseFragment<
            RegistrationViewState,
            RegistrationAction,
            RegistrationViewModel,
            FragmentRegisterBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?)
            = FragmentRegisterBinding.inflate(
        inflater,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            continueClicked()
        }
    }

    private fun continueClicked() {
        dispatchAction(RegistrationAction.RegisterClicked(
            binding.emailEdit.text,
            binding.passwordEdit.text,
            binding.passwordConfirm.text
        ))
    }

    override fun render(viewState: RegistrationViewState) {
        when(viewState){
            is RegistrationViewState.RegistrationSuccessful -> {
                findNavController().navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToPressureFragment())
            }
            is RegistrationViewState.EmailEmpty -> showEmailError()
            is RegistrationViewState.PasswordEmpty -> showPasswordError()
            is RegistrationViewState.PasswordsAreNotEqual -> showConfirmPassWordError()
        }
    }

    private fun showConfirmPassWordError() {
        binding.passwordConfirmLayout.error = "Пароли должны совпадать"
    }

    private fun showEmailError() {
        binding.emailEditLayout.error = "Почта не может быть пустой"
    }

    private fun showPasswordError(){
        binding.passwordLayout.error = "Пароль не может быть пустым"
    }
}