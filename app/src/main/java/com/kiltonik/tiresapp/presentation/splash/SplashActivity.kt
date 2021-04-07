package com.kiltonik.tiresapp.presentation.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kiltonik.tiresapp.presentation.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this,
                    "Нам действительно это нужно",
                    Toast.LENGTH_SHORT)
                    .show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        checkBluetoothPermission()
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
                        1)
//                    activityResultLauncher.launch(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
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
                return
            }
        }
    }
}