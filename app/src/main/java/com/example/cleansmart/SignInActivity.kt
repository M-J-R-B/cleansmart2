package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.LoginRequest
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.io.IOException

class SignInActivity : AppCompatActivity() {
    // UI components
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: MaterialButton
    private lateinit var signUpLink: TextView
    private lateinit var forgotPasswordText: TextView
    private lateinit var rememberMeCheckbox: CheckBox
    private lateinit var progressBar: CircularProgressIndicator
    
    // Storage manager
    private lateinit var secureStorageManager: SecureStorageManager
    private lateinit var sessionManager: SessionManager
    
    // API service
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        
        // Initialize managers
        secureStorageManager = SecureStorageManager.getInstance(this)
        sessionManager = SessionManager(this)
        apiService = ApiService.create()

        // Initialize UI components
        initializeViews()

        // Set click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        signUpLink = findViewById(R.id.signUpLink)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {
        // Sign In button click
        btnSignIn.setOnClickListener {
            if (validateInputs()) {
                // Show progress indicator
                progressBar.visibility = View.VISIBLE
                btnSignIn.visibility = View.INVISIBLE
                
                // Get email and password
                val email = etUsername.text.toString().trim()
                val password = etPassword.text.toString()
                
                // Call API to authenticate
                authenticateUser(email, password)
            }
        }

        // Sign Up link click
        signUpLink.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

        // Forgot password text click
        forgotPasswordText.setOnClickListener {
            Intent(this, ForgotPasswordActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    
    private fun authenticateUser(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)
                
                // Hide progress indicator
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnSignIn.visibility = View.VISIBLE
                }
                
                if (response.isSuccessful && response.body()?.success == true) {
                    // Authentication successful
                    val userData = response.body()?.user
                    if (userData != null) {
                        // Save user data to secure storage
                        secureStorageManager.saveUserId(userData.id)
                        secureStorageManager.saveEmail(userData.email)
                        secureStorageManager.setRememberMe(rememberMeCheckbox.isChecked)
                        
                        // Save to session manager
                        sessionManager.saveUserEmail(userData.email)
                        sessionManager.saveUserName(userData.fullName)
                        
                        runOnUiThread {
                            Toast.makeText(this@SignInActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            
                            // Navigate to landing activity
                            Intent(this@SignInActivity, LandingActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(it)
                                finish()
                            }
                        }
                    }
                } else {
                    // Authentication failed
                    val errorMessage = response.body()?.message ?: "Authentication failed"
                    runOnUiThread {
                        Toast.makeText(this@SignInActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                // Network error
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnSignIn.visibility = View.VISIBLE
                    Toast.makeText(
                        this@SignInActivity, 
                        "Network error: ${e.message}", 
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Other errors
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnSignIn.visibility = View.VISIBLE
                    Toast.makeText(
                        this@SignInActivity, 
                        "Error: ${e.message}", 
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate email
        val email = etUsername.text.toString().trim()
        if (email.isEmpty()) {
            emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Enter a valid email address"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        // Validate password
        val password = etPassword.text.toString()
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }
}