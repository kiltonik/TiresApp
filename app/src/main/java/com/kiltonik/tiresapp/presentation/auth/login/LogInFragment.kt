package com.kiltonik.tiresapp.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.kiltonik.tiresapp.R
import com.kiltonik.tiresapp.databinding.FragmentLoginBinding
import com.kiltonik.tiresapp.presentation.base.BaseFragment

class LogInFragment :
    BaseFragment<
            LoginViewState,
            LoginAction,
            LoginViewModel,
            FragmentLoginBinding>(){

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) = FragmentLoginBinding.inflate(
        inflater,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            dispatchAction(LoginAction.LogInClicked(
                binding.emailEdit.text,
                binding.passwordEdit.text
            ))
        }
    }

    override fun render(viewState: LoginViewState) {
        when(viewState){
            is LoginViewState.AuthSuccess -> {}
            is LoginViewState.PasswordEmpty -> {}
            is LoginViewState.EmailEmpty -> {}
        }
    }
}