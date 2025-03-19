package com.kiltonik.tiresapp.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kiltonik.tiresapp.App
import com.kiltonik.tiresapp.MainNavigationDirections
import com.kiltonik.tiresapp.R
import com.kiltonik.tiresapp.databinding.ActivityMainBinding
import com.kiltonik.tiresapp.repository.BluetoothRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var bluetoothRepository: BluetoothRepository

    private lateinit var binding: ActivityMainBinding

    private val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            checkAuth()
        } else {
            Toast.makeText(this,
                    "Нам действительно это нужно",
                    Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        checkAuth()
    }

    private fun checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(baseContext,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestPermissions(
                        arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        PERMISSION_CODE)
//                    activityResultLauncher.launch(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                )
            else checkAuth()
        }
        else checkAuth()
    }

    private fun checkAuth(){
        if(auth.currentUser == null) {
            findNavController(R.id.fragment_container)
                    .addOnDestinationChangedListener { _, destination, _ ->
                        if(SCREENS_WITHOUT_BOTTOM_BAR.contains(destination.id))
                            binding.bottomBar.visibility = GONE
                        else
                            binding.bottomBar.visibility = VISIBLE
                    }

            findNavController(R.id.fragment_container)
                    .navigate(MainNavigationDirections.actionGlobalEnterFragment())

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkAuth()
                    Toast.makeText(this,
                            "OK",
                            Toast.LENGTH_SHORT)
                            .show()
                } else {
                    Toast.makeText(this,
                            "Нам действительно это нужно",
                            Toast.LENGTH_SHORT)
                            .show()
                    finish()
                }

                // Some test
                val a = "something"
                return
            }
        }
    }

    companion object{
        private val SCREENS_WITHOUT_BOTTOM_BAR = listOf(
                R.id.enterFragment,
                R.id.registrationFragment,
                R.id.logInFragment
        )

        private const val PERMISSION_CODE = 1
    }
}
