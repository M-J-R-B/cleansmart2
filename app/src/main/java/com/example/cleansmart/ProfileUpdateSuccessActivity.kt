package com.example.cleansmart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.os.Handler
import android.os.Looper

class ProfileUpdateSuccessActivity : Activity() {

    private lateinit var backButton: ImageButton
    private lateinit var continueButton: Button
    private lateinit var updatedNameValue: TextView
    private lateinit var updatedEmailValue: TextView
    private lateinit var updatedUsernameValue: TextView
    private lateinit var updatedPhoneValue: TextView
    private lateinit var updatedAddressValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update_success)

        // Initialize views
        initViews()

        // Display updated profile information
        displayUpdatedInfo()

        // Set up button click listeners
        setupClickListeners()

        // Set up automatic return timer (optional)
        setupAutoReturn()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        continueButton = findViewById(R.id.continueButton)
        updatedNameValue = findViewById(R.id.updatedNameValue)
        updatedEmailValue = findViewById(R.id.updatedEmailValue)
        updatedUsernameValue = findViewById(R.id.updatedUsernameValue)
        updatedPhoneValue = findViewById(R.id.updatedPhoneValue)
        updatedAddressValue = findViewById(R.id.updatedAddressValue)
    }

    private fun displayUpdatedInfo() {
        // Get updated profile information from intent
        intent.extras?.let { bundle ->
            updatedNameValue.text = bundle.getString("name", "")
            updatedEmailValue.text = bundle.getString("email", "")
            updatedUsernameValue.text = bundle.getString("username", "")
            updatedPhoneValue.text = bundle.getString("phone", "")
            updatedAddressValue.text = bundle.getString("address", "")
        }
    }

    private fun setupClickListeners() {
        // Back button returns to profile screen with updated data
        backButton.setOnClickListener {
            returnToProfile()
        }

        // Continue button returns to profile screen with updated data
        continueButton.setOnClickListener {
            returnToProfile()
        }
    }

    private fun setupAutoReturn() {
        // Automatically return to profile screen after 3 seconds (optional)
        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing) {
                returnToProfile()
            }
        }, 3000) // 3 seconds delay
    }

    private fun returnToProfile() {
        // Create result intent with the updated profile data
        val resultIntent = Intent().apply {
            // Pass all the updated data
            putExtras(intent)
        }

        // Set result and finish
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onBackPressed() {
        // Handle back button press
        returnToProfile()
    }
}