package com.kiltonik.tiresapp.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.kiltonik.tiresapp.repository.EnterRepository
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Класс описывает логику обращений к классам Data слоя для получения данных
 */
class EnterInteractor(private val repository: EnterRepository) {

    fun registerUser(email: String, password: String): Task<AuthResult> {
        return repository.registerUser(
            email = email,
            password = password
        )
    }

    fun loginUser(email: String, password: String): Task<AuthResult> {
        return repository.authUser(
            email = email,
            password = password
        )
    }
}