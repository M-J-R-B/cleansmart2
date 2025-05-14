package com.example.cleansmart

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cleansmart.viewmodels.ForgotPasswordViewModel
import com.example.cleansmart.viewmodels.ForgotPasswordState

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]

        // Initialize views
        emailEditText = findViewById(R.id.emailPhoneEditText)
        resetButton = findViewById(R.id.signInButton)
        backButton = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)

        // Hide unused views
        findViewById<EditText>(R.id.codeEditText).visibility = View.GONE
        findViewById<Button>(R.id.resendCodeButton).visibility = View.GONE

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            val email = emailEditText.text.toString()
            if (validateEmail(email)) {
                viewModel.requestPasswordReset(email)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.forgotPasswordState.observe(this) { state ->
            when (state) {
                is ForgotPasswordState.Loading -> showLoading(true)
                is ForgotPasswordState.Success -> {
                    showLoading(false)
                    showSuccess(state.message)
                    // Navigate back to SignInActivity after a short delay
                    resetButton.postDelayed({
                        finish()
                    }, 1500)
                }
                is ForgotPasswordState.Error -> {
                    showLoading(false)
                    showError(state.message)
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email address"
            return false
        }
        return true
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        resetButton.isEnabled = !show
        emailEditText.isEnabled = !show
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}