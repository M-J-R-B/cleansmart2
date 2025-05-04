package com.example.cleansmart

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.utils.PasswordValidator

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var passwordStrengthBar: ProgressBar
    private lateinit var passwordStrengthText: TextView
    private lateinit var successLayout: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Initialize views
        initializeViews()

        // Set up listeners
        setupListeners()
    }

    private fun initializeViews() {
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)
        passwordStrengthBar = findViewById(R.id.passwordStrengthBar)
        passwordStrengthText = findViewById(R.id.passwordStrengthText)
        successLayout = findViewById(R.id.successCheck)

        // Hide progress bar and success layout initially
        progressBar.visibility = View.GONE
        successLayout.visibility = View.GONE
    }

    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Save button
        saveButton.setOnClickListener {
            if (validateInputs()) {
                // Show loading indicator
                showLoading(true)

                // Simulate network delay (remove in production)
                simulateNetworkDelay {
                    updatePassword()
                }
            }
        }

        // Password strength analyzer
        newPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                updatePasswordStrengthIndicator(password)

                // Check if passwords match on each change
                val confirmPassword = confirmPasswordEditText.text.toString()
                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    confirmPasswordEditText.error = "Passwords do not match"
                } else {
                    confirmPasswordEditText.error = null
                }
            }
        })

        // Confirm password matcher
        confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val confirmPassword = s.toString()
                val password = newPasswordEditText.text.toString()

                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    confirmPasswordEditText.error = "Passwords do not match"
                } else {
                    confirmPasswordEditText.error = null
                }
            }
        })
    }

    private fun updatePasswordStrengthIndicator(password: String) {
        // Use the PasswordValidator to calculate strength
        val strength = PasswordValidator.calculateStrength(password)

        // Update progress bar
        passwordStrengthBar.progress = strength

        // Update color based on strength
        val color = when {
            strength < 30 -> Color.RED
            strength < 60 -> Color.YELLOW
            else -> Color.GREEN
        }
        passwordStrengthBar.progressTintList = android.content.res.ColorStateList.valueOf(color)

        // If password is empty, show the requirements
        if (password.isEmpty()) {
            passwordStrengthText.text = "Password must be at least 8 characters with numbers and letters"
            passwordStrengthText.setTextColor(Color.WHITE)
        } else {
            // Get the strength message
            val message = PasswordValidator.getStrengthMessage(strength)
            passwordStrengthText.text = message
            passwordStrengthText.setTextColor(color)

            // If the password is weak, show improvement suggestions
            if (strength < 50) {
                val suggestions = PasswordValidator.getImprovementSuggestions(password)
                if (suggestions.isNotEmpty()) {
                    val suggestionText = suggestions.joinToString(", ")
                    passwordStrengthText.text = "$message - $suggestionText"
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        // Temporarily bypass validation by always returning true
        return true
        
        /* Original validation code (currently unreachable)
        val oldPassword = oldPasswordEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        // Clear previous errors
        oldPasswordEditText.error = null
        newPasswordEditText.error = null
        confirmPasswordEditText.error = null

        // Check if fields are empty
        if (oldPassword.isEmpty()) {
            oldPasswordEditText.error = "Please enter your current password"
            oldPasswordEditText.requestFocus()
            return false
        }

        if (newPassword.isEmpty()) {
            newPasswordEditText.error = "Please enter a new password"
            newPasswordEditText.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.error = "Please confirm your new password"
            confirmPasswordEditText.requestFocus()
            return false
        }

        // Check if new password and confirm password match
        if (!PasswordValidator.passwordsMatch(newPassword, confirmPassword)) {
            confirmPasswordEditText.error = "Passwords do not match"
            confirmPasswordEditText.requestFocus()
            return false
        }

        // Check if old and new passwords are the same
        if (oldPassword == newPassword) {
            newPasswordEditText.error = "New password must be different from the old password"
            newPasswordEditText.requestFocus()
            return false
        }

        // Check if password meets minimum requirements
        if (!PasswordValidator.meetsMinimumRequirements(newPassword)) {
            val suggestions = PasswordValidator.getImprovementSuggestions(newPassword)
            val errorMessage = "Password does not meet requirements: " + suggestions.joinToString(", ")
            newPasswordEditText.error = errorMessage
            newPasswordEditText.requestFocus()
            return false
        }

        // Check password strength
        val strength = PasswordValidator.calculateStrength(newPassword)
        if (strength < 50) {
            newPasswordEditText.error = "Password is too weak. Add uppercase, numbers, or special characters."
            newPasswordEditText.requestFocus()
            return false
        }

        return true
        */
    }

    private fun updatePassword() {
        showLoading(true)
        
        // Simulate password update success
        saveButton.postDelayed({
            showLoading(false)
            showSuccess()
        }, 1500)
    }

    private fun showSuccess() {
        successLayout.visibility = View.VISIBLE
        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
        
        // Return to previous screen after a delay
        successLayout.postDelayed({
            setResult(Activity.RESULT_OK)
            finish()
        }, 1500)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        saveButton.isEnabled = !show
        oldPasswordEditText.isEnabled = !show
        newPasswordEditText.isEnabled = !show
        confirmPasswordEditText.isEnabled = !show
    }

    private fun simulateNetworkDelay(callback: () -> Unit) {
        // Simulate network delay (1.5 seconds)
        // Remove this in production and replace with actual API calls
        Thread {
            Thread.sleep(1500)
            callback()
        }.start()
    }
}