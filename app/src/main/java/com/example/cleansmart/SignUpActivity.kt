package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
<<<<<<< HEAD
    private lateinit var binding: ActivitySignupBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var secureStorage: SecureStorageManager
    private val TAG = "SignUpActivity"
    private val gson = Gson()
=======
    // UI components
    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    
    private lateinit var btnSignUp: MaterialButton
    private lateinit var signInLink: TextView
    private lateinit var backButton: ImageButton
    private lateinit var progressBar: CircularProgressIndicator
>>>>>>> 086021e2a7e25b0261746b47f0be3ba38a178411

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

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
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        
        btnSignUp = findViewById(R.id.btnSignUp)
        signInLink = findViewById(R.id.signInLink)
        backButton = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {
        // Back button click
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Sign Up button click
        btnSignUp.setOnClickListener {
            if (validateInputs()) {
                // Show progress indicator
                progressBar.visibility = View.VISIBLE
                btnSignUp.visibility = View.INVISIBLE
                
<<<<<<< HEAD
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString()

                Log.d(TAG, "Attempting registration with fullName: $name, email: $email")
                Log.d(TAG, "Network available: ${isNetworkAvailable()}")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d(TAG, "Making signup network request")
                        val signupRequest = SignupRequest(fullName = name, email = email, password = password)
                        Log.d(TAG, "Request body: $signupRequest")
                        
                        val response = NetworkClient.apiService.signup(signupRequest)
                        Log.d(TAG, "Response received: ${response.code()}")
                        
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSignUp.visibility = View.VISIBLE

                            when {
                                response.isSuccessful && response.body()?.success == true -> {
                                    val user = response.body()?.user
                                    if (user != null) {
                                        Log.d(TAG, "Registration successful for user: ${user.email}")
                                        sessionManager.saveUserName(user.fullName)
                                        sessionManager.saveUserEmail(user.email)
                                        secureStorage.saveEmail(user.email)
                                        secureStorage.saveUserId(user.id)

                                        Toast.makeText(this@SignUpActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                                        
                                        Intent(this@SignUpActivity, SignInActivity::class.java).also { 
                                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(it)
                                            finish()
                                        }
                                    }
                                }
                                else -> {
                                    val errorMessage = response.body()?.message ?: "Registration failed"
                                    Toast.makeText(this@SignUpActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSignUp.visibility = View.VISIBLE
                            Toast.makeText(this@SignUpActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
=======
                // Here you would normally implement registration
                // For now, just simulate a delay and success
                btnSignUp.postDelayed({
                    progressBar.visibility = View.GONE
                    btnSignUp.visibility = View.VISIBLE
                    
                    // Just a placeholder until authentication is implemented
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    
                    // Navigate to sign in screen
                    Intent(this, SignInActivity::class.java).also { 
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                        finish()
>>>>>>> 086021e2a7e25b0261746b47f0be3ba38a178411
                    }
                }, 1500)
            }
        }

        // Sign In link click
        signInLink.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validateInputs(): Boolean {
        // Temporarily bypass validation by always returning true
        return true
        
        // Original validation code (currently unreachable)
        var isValid = true
        
        // Validate name
        val name = etName.text.toString().trim()
        if (name.isEmpty()) {
            nameInputLayout.error = "Name is required"
            isValid = false
        } else {
            nameInputLayout.error = null
        }
        
        // Validate email
        val email = etEmail.text.toString().trim()
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
        
        // Validate confirm password
        val confirmPassword = etConfirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = "Please confirm your password"
            isValid = false
        } else if (confirmPassword != password) {
            confirmPasswordInputLayout.error = "Passwords do not match"
            isValid = false
        } else {
            confirmPasswordInputLayout.error = null
        }
        
        return isValid
    }
} 