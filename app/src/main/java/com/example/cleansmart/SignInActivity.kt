package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivitySigninBinding
import com.example.cleansmart.network.LoginRequest
import com.example.cleansmart.network.NetworkClient
import com.example.cleansmart.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            if (validateInputs()) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSignIn.visibility = View.INVISIBLE
                
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
                                    sessionManager.saveUserName(user.fullName)
                                    sessionManager.saveUserEmail(user.email)

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
                    }
                }
            }
        }

        binding.signUpLink.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        
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
        
        return isValid
    }
} 