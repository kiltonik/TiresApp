package com.kiltonik.tiresapp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kiltonik.tiresapp.R
import com.kiltonik.tiresapp.databinding.FragmentEnterBinding

class EnterFragment : Fragment() {

    private lateinit var binding: FragmentEnterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterBinding.inflate(
            inflater,
            container,
            false 
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            it.findNavController().navigate(R.id.action_enterFragment_to_logInFragment)
        }

        binding.registration.setOnClickListener {
            it.findNavController().navigate(R.id.action_enterFragment_to_registrationFragment)
        }
    }
}