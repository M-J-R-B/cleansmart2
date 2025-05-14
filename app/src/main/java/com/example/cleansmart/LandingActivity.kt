package com.example.cleansmart

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.cleansmart.activities.ProfileActivity
import com.example.cleansmart.databinding.ActivityLandingBinding
import com.example.cleansmart.utils.SecureStorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Image captured successfully", Toast.LENGTH_SHORT).show()
            // Handle the captured image here
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For now, bypass login check and directly setup UI
        setupClickListeners()
        
        // Uncomment this section when implementing real authentication
        /*
        // Check login status in a coroutine
        lifecycleScope.launch(Dispatchers.IO) {
            val secureStorage = SecureStorageManager.getInstance(this@LandingActivity)
            val isLoggedIn = secureStorage.getUserId() != null

            withContext(Dispatchers.Main) {
                if (!isLoggedIn) {
                    navigateToLogin()
                    return@withContext
                }
                setupClickListeners()
            }
        }
        */
    }

    private fun navigateToLogin() {
        val loginIntent = Intent(this, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(loginIntent)
        finish()
    }

    private fun setupClickListeners() {
        // Bottom navigation setup
        binding.bottomNavigation.profileButton.setOnClickListener {
            Log.d("Android Assignment", "Profile button clicked")
            
            // Navigate to profile screen
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.bottomNavigation.settingsButton.setOnClickListener {
            Log.d("Android Assignment", "Settings button clicked")
            try {
                startActivity(Intent(this, SettingsActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Settings: ${e.message}")
                Toast.makeText(this, "Error: Settings screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bottomNavigation.homeButton.setOnClickListener {
            Log.d("Android Assignment", "Home button clicked")
            // Already on home/landing screen, no need to navigate
        }

        binding.bottomNavigation.tasksButton.setOnClickListener {
            Log.d("Android Assignment", "Tasks button clicked")
            try {
                startActivity(Intent(this, TasksActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Tasks: ${e.message}")
                Toast.makeText(this, "Error: Tasks screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        // Quick Actions setup
        binding.cameraAction.setOnClickListener {
            checkCameraPermissionAndOpen()
        }

        binding.scanAction.setOnClickListener {
            // TODO: Implement QR code scanning functionality
            Toast.makeText(this, "QR code scanning coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.addTaskAction.setOnClickListener {
            try {
                startActivity(Intent(this, TasksActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Tasks: ${e.message}")
                Toast.makeText(this, "Error: Tasks screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        // Floating action button setup
        binding.cameraFab.setOnClickListener {
            checkCameraPermissionAndOpen()
        }
    }

    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            Log.e("Android Assignment", "Error opening camera: ${e.message}")
        }
    }
}