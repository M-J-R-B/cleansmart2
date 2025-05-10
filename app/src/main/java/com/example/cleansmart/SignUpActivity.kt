package com.example.cleansmart

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivitySignupBinding
import com.example.cleansmart.network.NetworkClient
import com.example.cleansmart.network.SignupRequest
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var sessionManager: SessionManager
    private val TAG = "SignUpActivity"
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        setupClickListeners()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnSignUp.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "No internet connection available", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (validateInputs()) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSignUp.visibility = View.INVISIBLE
                
                val fullName = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString()

                Log.d(TAG, "Attempting registration with fullName: $fullName, email: $email")
                Log.d(TAG, "Network available: ${isNetworkAvailable()}")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d(TAG, "Making signup network request")
                        val signupRequest = SignupRequest(fullName, email, password)
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
                    }
                }
            }
        }

        binding.signInLink.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        
        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()) {
            binding.nameInputLayout.error = "Full name is required"
            isValid = false
        } else {
            binding.nameInputLayout.error = null
        }
        
        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Enter a valid email address"
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }
        
        val password = binding.etPassword.text.toString()
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }
        
        val confirmPassword = binding.etConfirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputLayout.error = "Please confirm your password"
            isValid = false
        } else if (confirmPassword != password) {
            binding.confirmPasswordInputLayout.error = "Passwords do not match"
            isValid = false
        } else {
            binding.confirmPasswordInputLayout.error = null
        }
        
        return isValid
    }
} 