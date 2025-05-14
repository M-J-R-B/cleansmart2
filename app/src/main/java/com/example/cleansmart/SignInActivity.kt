package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import com.example.cleansmart.databinding.ActivitySigninBinding
import com.example.cleansmart.network.LoginRequest
import com.example.cleansmart.network.NetworkClient
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var secureStorage: SecureStorageManager
=======
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
>>>>>>> 086021e2a7e25b0261746b47f0be3ba38a178411

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

<<<<<<< HEAD
        sessionManager = SessionManager(this)
        secureStorage = SecureStorageManager.getInstance(this)
=======
        // Initialize UI components
        initializeViews()
        
        // Set click listeners
>>>>>>> 086021e2a7e25b0261746b47f0be3ba38a178411
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
                
<<<<<<< HEAD
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = NetworkClient.apiService.login(
                            LoginRequest(
                                email = email,
                                password = password
                            )
                        )

                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSignIn.visibility = View.VISIBLE

                            if (response.isSuccessful && response.body()?.success == true) {
                                val user = response.body()?.user
                                if (user != null) {
                                    // Save user data in both storage managers
                                    sessionManager.saveUserName(user.fullName)
                                    sessionManager.saveUserEmail(user.email)
                                    secureStorage.saveEmail(user.email)
                                    secureStorage.saveUserId(user.id)

                                    Toast.makeText(this@SignInActivity, "Sign in successful!", Toast.LENGTH_SHORT).show()
                                    
                                    Intent(this@SignInActivity, LandingActivity::class.java).also { 
                                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                }
                            } else {
                                val errorMessage = response.body()?.message ?: "Sign in failed"
                                Toast.makeText(this@SignInActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSignIn.visibility = View.VISIBLE
                            Toast.makeText(this@SignInActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
=======
                // Here you would normally implement authentication
                // For now, just simulate a delay and success
                btnSignIn.postDelayed({
                    progressBar.visibility = View.GONE
                    btnSignIn.visibility = View.VISIBLE
                    
                    // Just a placeholder until authentication is implemented
                    Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show()
                    
                    // Navigate to landing activity
                    Intent(this, LandingActivity::class.java).also { 
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                        finish()
>>>>>>> 086021e2a7e25b0261746b47f0be3ba38a178411
                    }
                }, 1500)
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

    private fun validateInputs(): Boolean {
        // Temporarily bypass validation by always returning true
        return true
        
        // Original validation code (currently unreachable)
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