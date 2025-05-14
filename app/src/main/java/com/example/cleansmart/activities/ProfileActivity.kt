package com.example.cleansmart.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cleansmart.databinding.ActivityProfileBinding
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import com.google.android.material.appbar.AppBarLayout
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.cleansmart.R
import com.example.cleansmart.network.NetworkClient
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var secureStorageManager: SecureStorageManager
    private val TAG = "ProfileActivity"
    
    private val PERMISSION_REQUEST_CODE = 100
    
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri = result.data?.data
                imageUri?.let {
                    try {
                        // Instead of directly setting the URI, save a local copy
                        val localImageFile = saveImageToLocalStorage(it)
                        if (localImageFile != null) {
                            binding.profileImage.setImageBitmap(BitmapFactory.decodeFile(localImageFile.absolutePath))
                            saveProfileImage(localImageFile.absolutePath)
                        } else {
                            // Fallback to default image if saving fails
                            binding.profileImage.setImageResource(R.drawable.default_profile)
                            Toast.makeText(this, "Could not save profile image", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error setting profile image: ${e.message}", e)
                        Toast.makeText(this, "Could not update profile image", Toast.LENGTH_SHORT).show()
                        binding.profileImage.setImageResource(R.drawable.default_profile)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling image pick result: ${e.message}", e)
            binding.profileImage.setImageResource(R.drawable.default_profile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityProfileBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            // Ensure NetworkClient is initialized
            try {
                NetworkClient.initialize(applicationContext)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize NetworkClient: ${e.message}", e)
                // Continue without NetworkClient as it's not critical for this screen
            }

            // Initialize managers
            sessionManager = SessionManager(this)
            secureStorageManager = SecureStorageManager.getInstance(this)

            setupToolbar()
            loadUserProfile()
            setupClickListeners()
        } catch (e: Exception) {
            Log.e(TAG, "Error during onCreate: ${e.message}", e)
            Toast.makeText(this, "Error initializing profile screen", Toast.LENGTH_SHORT).show()
            finish() // Return to previous screen if we can't initialize properly
        }
    }

    private fun setupToolbar() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up toolbar: ${e.message}", e)
        }
    }

    private fun loadUserProfile() {
        try {
            // Load from SessionManager
            val userName = sessionManager.getUserName() ?: "Your Name"
            binding.profileName.text = userName

            // Load from SecureStorageManager
            val email = secureStorageManager.getEmail() ?: "email@example.com"
            binding.emailValue.text = email
            
            // Load additional user data from SharedPreferences
            val sharedPrefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
            
            // Set username
            val username = sharedPrefs.getString("username", "username")
            binding.usernameValue.text = username
            
            // Set phone number
            val phone = sharedPrefs.getString("phone", "+1234567890")
            binding.phoneValue.text = phone
            
            // Set address
            val address = sharedPrefs.getString("address", "Your Address")
            binding.addressValue.text = address
            
            // Set style preference
            val stylePreference = sharedPrefs.getString("stylePreference", "Default")
            binding.styleValue.text = stylePreference
            
            // Set profile image if available - use local path instead of URI to avoid permission issues
            val profileImagePath = sharedPrefs.getString("profileImagePath", null)
            if (profileImagePath != null) {
                try {
                    val file = File(profileImagePath)
                    if (file.exists()) {
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        binding.profileImage.setImageBitmap(bitmap)
                    } else {
                        binding.profileImage.setImageResource(R.drawable.default_profile)
                    }
                } catch (e: Exception) {
                    // If there's an error loading the image, log it but don't crash
                    Log.e(TAG, "Error loading profile image: ${e.message}", e)
                    binding.profileImage.setImageResource(R.drawable.default_profile)
                }
            } else {
                binding.profileImage.setImageResource(R.drawable.default_profile)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading user profile: ${e.message}", e)
            Toast.makeText(this, "Could not load profile data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        try {
            binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                try {
                    val scrollRange = appBarLayout.totalScrollRange
                    val percentage = Math.abs(verticalOffset).toFloat() / scrollRange.toFloat()

                    // Fade out the profile image and name as user scrolls
                    binding.profileImage.alpha = 1 - percentage
                } catch (e: Exception) {
                    Log.e(TAG, "Error in app bar offset change: ${e.message}", e)
                }
            })
            
            // Set click listener for profile image to allow uploads
            binding.profileImage.setOnClickListener {
                try {
                    checkPermissionAndOpenGallery()
                } catch (e: Exception) {
                    Log.e(TAG, "Error handling profile image click: ${e.message}", e)
                    Toast.makeText(this, "Could not access image picker", Toast.LENGTH_SHORT).show()
                }
            }

            binding.editButton.setOnClickListener {
                try {
                    val intent = Intent(this, EditProfileActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e(TAG, "Error opening edit profile screen: ${e.message}", e)
                    Toast.makeText(this, "Error opening edit profile screen", Toast.LENGTH_SHORT).show()
                }
            }

            binding.changePasswordButton.setOnClickListener {
                try {
                    // Make sure NetworkClient is properly initialized before proceeding
                    try {
                        NetworkClient.initialize(applicationContext)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to initialize NetworkClient for password change: ${e.message}", e)
                    }
                    
                    if (secureStorageManager.getEmail() == null) {
                        Toast.makeText(this, "You need to be logged in to change your password", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    // Log the detailed error
                    Log.e(TAG, "Error when trying to open change password screen", e)
                    Toast.makeText(this, "Error opening change password screen", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up click listeners: ${e.message}", e)
        }
    }
    
    private fun checkPermissionAndOpenGallery() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // Android 13+ uses READ_MEDIA_IMAGES permission
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) 
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            } else {
                // Android 12 and below use READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) 
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking permissions: ${e.message}", e)
            Toast.makeText(this, "Could not verify permissions", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun openGallery() {
        try {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error opening gallery: ${e.message}", e)
            Toast.makeText(this, "Could not open gallery", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun saveImageToLocalStorage(imageUri: Uri): File? {
        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            if (inputStream != null) {
                // Create a directory for app images if it doesn't exist
                val imagesDir = File(filesDir, "profile_images")
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs()
                }
                
                // Create a file to save the image
                val imageFile = File(imagesDir, "profile_image_${System.currentTimeMillis()}.jpg")
                
                // Copy the image data to the file
                val outputStream = FileOutputStream(imageFile)
                inputStream.copyTo(outputStream)
                
                // Close streams
                inputStream.close()
                outputStream.close()
                
                return imageFile
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving image to local storage: ${e.message}", e)
        }
        return null
    }
    
    private fun saveProfileImage(imagePath: String) {
        try {
            val sharedPrefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
            sharedPrefs.edit().putString("profileImagePath", imagePath).apply()
            
            // Remove old URI-based storage to avoid crashes
            sharedPrefs.edit().remove("profileImageUri").apply()
            
            Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving profile image: ${e.message}", e)
            Toast.makeText(this, "Could not save profile image", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() 
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied to access your photos", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling permission result: ${e.message}", e)
        }
    }

    override fun onResume() {
        try {
            super.onResume()
            // Reload user profile data when returning to this screen
            loadUserProfile()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onResume: ${e.message}", e)
        }
    }
}