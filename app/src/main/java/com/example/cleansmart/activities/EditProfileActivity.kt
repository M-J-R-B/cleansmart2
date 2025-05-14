package com.example.cleansmart.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.cleansmart.R
import com.example.cleansmart.utils.SecureStorageManager

class EditProfileActivity : Activity() {

    // Constants
    private val PICK_IMAGE_REQUEST = 1
    private val PROFILE_UPDATE_SUCCESS_REQUEST = 2

    // UI Components
    private lateinit var backButton: ImageButton
    private lateinit var profileImage: ImageView
    private lateinit var editPhotoButton: ImageView
    private lateinit var changePhotoText: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var styleSpinner: Spinner
    private lateinit var saveButton: Button

    // Profile data to edit
    private data class ProfileData(
        var name: String = "",
        var email: String = "",
        var username: String = "",
        var phone: String = "",
        var address: String = "",
        var stylePreference: String = "Default"
    )

    private val profileData = ProfileData()
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize views
        initViews()

        // Set up the spinner with style options
        setupStyleSpinner()

        // Load existing profile data from Intent
        loadProfileData()

        // Set up click listeners
        setupClickListeners()

        // Set focus on specific field if requested
        handleFieldFocus()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        profileImage = findViewById(R.id.profileImage)
        editPhotoButton = findViewById(R.id.editPhotoButton)
        changePhotoText = findViewById(R.id.changePhotoText)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        styleSpinner = findViewById(R.id.styleSpinner)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun setupStyleSpinner() {
        // Create an ArrayAdapter with style options
        val styleOptions = arrayOf("Default", "Minimalist", "Classic", "Modern", "Colorful")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, styleOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        styleSpinner.adapter = adapter

        // Set spinner selection listener
        styleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profileData.stylePreference = styleOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun loadProfileData() {
        // Get profile data from intent extras
        intent.extras?.let { bundle ->
            profileData.name = bundle.getString("name", "")
            profileData.email = bundle.getString("email", "")
            profileData.username = bundle.getString("username", "")
            profileData.phone = bundle.getString("phone", "")
            profileData.address = bundle.getString("address", "")
            profileData.stylePreference = bundle.getString("stylePreference", "Default")
        }

        // Set the data to UI elements
        nameEditText.setText(profileData.name)
        emailEditText.setText(profileData.email)
        usernameEditText.setText(profileData.username)
        phoneEditText.setText(profileData.phone)
        addressEditText.setText(profileData.address)

        // Set spinner selection based on style preference
        val styleOptions = arrayOf("Default", "Minimalist", "Classic", "Modern", "Colorful")
        val styleIndex = styleOptions.indexOf(profileData.stylePreference)
        if (styleIndex != -1) {
            styleSpinner.setSelection(styleIndex)
        }
    }

    private fun handleFieldFocus() {
        // Focus on specific field if requested
        when (intent.getStringExtra("focus_field")?.toLowerCase()) {
            "email" -> emailEditText.requestFocus()
            "username" -> usernameEditText.requestFocus()
            "phone" -> phoneEditText.requestFocus()
            "address" -> addressEditText.requestFocus()
            "style" -> styleSpinner.performClick()
            "name" -> nameEditText.requestFocus()
            else -> { /* No specific focus */ }
        }
    }

    private fun setupClickListeners() {
        // Back button - return to previous screen
        backButton.setOnClickListener {
            finish() // Just close the current activity and return to profile
        }

        // Photo editing options
        editPhotoButton.setOnClickListener {
            openImagePicker()
        }

        changePhotoText.setOnClickListener {
            openImagePicker()
        }

        // Save button
        saveButton.setOnClickListener {
            if (validateInputs()) {
                saveProfileChanges()
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            selectedImageUri = data.data

            // Update the profile image view
            selectedImageUri?.let {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                    profileImage.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("EditProfileActivity", "Error loading image: ${e.message}")
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == PROFILE_UPDATE_SUCCESS_REQUEST && resultCode == RESULT_OK) {
            // Forward the result from success screen to profile activity
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        // Clear previous errors
        nameEditText.error = null
        emailEditText.error = null
        usernameEditText.error = null
        phoneEditText.error = null
        addressEditText.error = null

        // Validate name
        if (nameEditText.text.toString().trim().isEmpty()) {
            nameEditText.error = "Name is required"
            nameEditText.requestFocus()
            return false
        }

        // Validate email
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email address"
            emailEditText.requestFocus()
            return false
        }

        // Validate username
        if (usernameEditText.text.toString().trim().isEmpty()) {
            usernameEditText.error = "Username is required"
            usernameEditText.requestFocus()
            return false
        }

        // Validate phone (optional)
        val phone = phoneEditText.text.toString().trim()
        if (phone.isNotEmpty() && !android.util.Patterns.PHONE.matcher(phone).matches()) {
            phoneEditText.error = "Please enter a valid phone number"
            phoneEditText.requestFocus()
            return false
        }

        return true
    }

    private fun saveProfileChanges() {
        // Update profile data with current values
        profileData.name = nameEditText.text.toString().trim()
        profileData.email = emailEditText.text.toString().trim()
        profileData.username = usernameEditText.text.toString().trim()
        profileData.phone = phoneEditText.text.toString().trim()
        profileData.address = addressEditText.text.toString().trim()

        // Save to SharedPreferences
        val sharedPrefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString("name", profileData.name)
            putString("username", profileData.username)
            putString("phone", profileData.phone)
            putString("address", profileData.address)
            putString("stylePreference", profileData.stylePreference)
            selectedImageUri?.let { putString("profileImageUri", it.toString()) }
            apply()
        }

        // Save email to secure storage
        val secureStorage = SecureStorageManager.getInstance(this)
        secureStorage.saveEmail(profileData.email)

        // Create result intent with updated data
        val resultIntent = Intent().apply {
            putExtra("name", profileData.name)
            putExtra("email", profileData.email)
            putExtra("username", profileData.username)
            putExtra("phone", profileData.phone)
            putExtra("address", profileData.address)
            putExtra("stylePreference", profileData.stylePreference)
            selectedImageUri?.let { putExtra("imageUri", it.toString()) }
        }

        // Set result and finish
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}