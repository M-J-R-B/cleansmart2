package com.example.cleansmart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cleansmart.utils.SecureStorageManager

class ProfileActivity : Activity() {

    // Constants
    private val EDIT_PROFILE_REQUEST_CODE = 100

    // Cached view references
    private lateinit var backButton: ImageButton
    private lateinit var editProfileButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var emailEditIcon: ImageView
    private lateinit var usernameEditIcon: ImageView
    private lateinit var phoneEditIcon: ImageView
    private lateinit var addressEditIcon: ImageView
    private lateinit var styleSelector: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        actionBar?.hide()

        // Initialize view references
        initViews()

        // Set up click listeners
        setupClickListeners()

        // Load profile data
        loadProfileData()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        editProfileButton = findViewById(R.id.editButton)
        changePasswordButton = findViewById(R.id.changePasswordButton)
        emailEditIcon = findViewById(R.id.editEmailIcon)
        usernameEditIcon = findViewById(R.id.editUsernameIcon)
        phoneEditIcon = findViewById(R.id.editPhoneIcon)
        addressEditIcon = findViewById(R.id.editAddressIcon)
        styleSelector = findViewById(R.id.styleSelectorIcon)
    }

    private fun setupClickListeners() {
        // Back button - return to previous screen
        backButton.setOnClickListener {
            Log.d("ProfileActivity", "Back button clicked")
            finish()
        }

        // Edit profile button
        editProfileButton.setOnClickListener {
            Log.d("ProfileActivity", "Edit profile button clicked")
            openEditProfileScreen()
        }

        // Change password button
        changePasswordButton.setOnClickListener {
            Log.d("Button Click", "Change Password Clicked")
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        // Edit icons for each field
        emailEditIcon.setOnClickListener {
            openEditProfileScreen("email")
        }

        usernameEditIcon.setOnClickListener {
            openEditProfileScreen("username")
        }

        phoneEditIcon.setOnClickListener {
            openEditProfileScreen("phone")
        }

        addressEditIcon.setOnClickListener {
            openEditProfileScreen("address")
        }

        // Style selector
        styleSelector.setOnClickListener {
            openEditProfileScreen("style")
        }
    }

    private fun loadProfileData() {
        // Load profile data from SharedPreferences
        val sharedPrefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val secureStorage = SecureStorageManager.getInstance(this)
        
        // Update UI with saved profile data
        findViewById<TextView>(R.id.profileName).text = sharedPrefs.getString("name", "Not set")
        // Get email from secure storage
        findViewById<TextView>(R.id.emailValue).text = secureStorage.getEmail() ?: "Not set"
        findViewById<TextView>(R.id.usernameValue).text = sharedPrefs.getString("username", "Not set")
        findViewById<TextView>(R.id.phoneValue).text = sharedPrefs.getString("phone", "Not set")
        findViewById<TextView>(R.id.addressValue).text = sharedPrefs.getString("address", "Not set")
        findViewById<TextView>(R.id.styleValue).text = sharedPrefs.getString("stylePreference", "Default")
        
        // Load profile image if exists
        sharedPrefs.getString("profileImageUri", null)?.let { uriString ->
            try {
                val imageUri = Uri.parse(uriString)
                findViewById<ImageView>(R.id.profileImage).setImageURI(imageUri)
            } catch (e: Exception) {
                Log.e("ProfileActivity", "Error loading profile image: ${e.message}")
            }
        }
    }

    private fun openEditProfileScreen(fieldToFocus: String? = null) {
        try {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                // Pass current profile data to edit screen
                putExtra("name", findViewById<TextView>(R.id.profileName).text.toString())
                putExtra("email", findViewById<TextView>(R.id.emailValue).text.toString())
                putExtra("username", findViewById<TextView>(R.id.usernameValue).text.toString())
                putExtra("phone", findViewById<TextView>(R.id.phoneValue).text.toString())
                putExtra("address", findViewById<TextView>(R.id.addressValue).text.toString())
                putExtra("stylePreference", findViewById<TextView>(R.id.styleValue).text.toString())

                // Optional: Pass which field to focus on
                if (fieldToFocus != null) {
                    putExtra("focus_field", fieldToFocus)
                }
            }

            // Start the edit activity with result handling
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        } catch (e: Exception) {
            Log.e("ProfileActivity", "Error opening edit profile screen", e)
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Process the returned data
            data?.let { intent ->
                // Update UI with new profile data
                intent.getStringExtra("name")?.let { name ->
                    findViewById<TextView>(R.id.profileName).text = name
                }

                intent.getStringExtra("email")?.let { email ->
                    findViewById<TextView>(R.id.emailValue).text = email
                }

                intent.getStringExtra("username")?.let { username ->
                    findViewById<TextView>(R.id.usernameValue).text = username
                }

                intent.getStringExtra("phone")?.let { phone ->
                    findViewById<TextView>(R.id.phoneValue).text = phone
                }

                intent.getStringExtra("address")?.let { address ->
                    findViewById<TextView>(R.id.addressValue).text = address
                }

                intent.getStringExtra("stylePreference")?.let { style ->
                    findViewById<TextView>(R.id.styleValue).text = style
                }

                // Update profile image if a new one was selected
                intent.getStringExtra("imageUri")?.let { uriString ->
                    try {
                        val imageUri = Uri.parse(uriString)
                        findViewById<ImageView>(R.id.profileImage).setImageURI(imageUri)
                    } catch (e: Exception) {
                        Log.e("ProfileActivity", "Error setting profile image: ${e.message}")
                    }
                }

                // Show success message
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDialog(fieldName: String, currentValue: String) {
        // This method is replaced by openEditProfileScreen
        // Keeping it for backward compatibility
        openEditProfileScreen(fieldName.toLowerCase())
    }

    private fun showStyleOptions() {
        // This method is replaced by openEditProfileScreen
        // Keeping it for backward compatibility
        openEditProfileScreen("style")
    }
}