package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivitySignupBinding
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.net.ConnectivityManager
import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.NetworkClient
import com.example.cleansmart.network.SignupRequest
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var secureStorage: SecureStorageManager
    private val TAG = "SignUpActivity"
    private val gson = Gson()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        secureStorage = SecureStorageManager.getInstance(this)
        
        // Initialize UI components
        initializeViews()

        // Set click listeners
        setupClickListeners()
        
        // Setup real-time validation
        setupTextWatchers()
    }

    private fun initializeViews() {
        nameInputLayout = binding.nameInputLayout
        emailInputLayout = binding.emailInputLayout
        passwordInputLayout = binding.passwordInputLayout
        confirmPasswordInputLayout = binding.confirmPasswordInputLayout

        etName = binding.etName
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        etConfirmPassword = binding.etConfirmPassword

        btnSignUp = binding.btnSignUp
        signInLink = binding.signInLink
        backButton = binding.backButton
        progressBar = binding.progressBar
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

                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString()

                Log.d(TAG, "Attempting registration with fullName: $name, email: $email")
                Log.d(TAG, "Network available: ${isNetworkAvailable()}")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d(TAG, "Making signup network request")
                        val signupRequest =
                            SignupRequest(fullName = name, email = email, password = password)
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
                                        Log.d(
                                            TAG,
                                            "Registration successful for user: ${user.email}"
                                        )
                                        sessionManager.saveUserName(user.fullName)
                                        sessionManager.saveUserEmail(user.email)
                                        secureStorage.saveEmail(user.email)
                                        secureStorage.saveUserId(user.id)

                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "Registration successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        Intent(
                                            this@SignUpActivity,
                                            SignInActivity::class.java
                                        ).also {
                                            it.flags =
                                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(it)
                                            finish()
                                        }
                                    }
                                }

                                else -> {
                                    val errorMessage =
                                        response.body()?.message ?: "Registration failed"
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    
                                    // Show diagnostic button for server errors
                                    binding.networkDiagnosticButton.visibility = View.VISIBLE
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error during signup: ${e.message}", e)
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSignUp.visibility = View.VISIBLE
                            Toast.makeText(
                                this@SignUpActivity,
                                "Network error: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            
                            // Show diagnostic button for network errors
                            binding.networkDiagnosticButton.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        // Sign In link click
        signInLink.setOnClickListener {
            onBackPressed()
        }
        
        // Network diagnostic button
        binding.networkDiagnosticButton.setOnClickListener {
            runNetworkDiagnostics()
        }
    }
    
    private fun setupTextWatchers() {
        // Real-time name validation
        binding.etName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateName()
            }
        }
        
        // Real-time email validation
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateEmail()
            }
        }
        
        // Real-time password validation
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword()
            }
        }
        
        // Real-time confirm password validation
        binding.etConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateConfirmPassword()
            }
        }
    }

    private fun validateInputs(): Boolean {
        return validateName() && validateEmail() && validatePassword() && validateConfirmPassword()
    }
    
    private fun validateName(): Boolean {
        val name = binding.etName.text.toString().trim()
        return when {
            name.isEmpty() -> {
                binding.nameInputLayout.error = "Name is required"
                false
            }
            name.length < 2 -> {
                binding.nameInputLayout.error = "Name is too short"
                false
            }
            else -> {
                binding.nameInputLayout.error = null
                true
            }
        }
    }
    
    private fun validateEmail(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return when {
            email.isEmpty() -> {
                binding.emailInputLayout.error = "Email is required"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInputLayout.error = "Enter a valid email address"
                false
            }
            else -> {
                binding.emailInputLayout.error = null
                true
            }
        }
    }
    
    private fun validatePassword(): Boolean {
        val password = binding.etPassword.text.toString()
        return when {
            password.isEmpty() -> {
                binding.passwordInputLayout.error = "Password is required"
                false
            }
            password.length < 6 -> {
                binding.passwordInputLayout.error = "Password must be at least 6 characters"
                false
            }
            !password.any { it.isDigit() } -> {
                binding.passwordInputLayout.error = "Password must contain at least one number"
                false
            }
            !password.any { it.isLetter() } -> {
                binding.passwordInputLayout.error = "Password must contain at least one letter"
                false
            }
            else -> {
                binding.passwordInputLayout.error = null
                true
            }
        }
    }
    
    private fun validateConfirmPassword(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        return when {
            confirmPassword.isEmpty() -> {
                binding.confirmPasswordInputLayout.error = "Please confirm your password"
                false
            }
            confirmPassword != password -> {
                binding.confirmPasswordInputLayout.error = "Passwords do not match"
                false
            }
            else -> {
                binding.confirmPasswordInputLayout.error = null
                true
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun runNetworkDiagnostics() {
        binding.diagnosticResultText.visibility = View.VISIBLE
        binding.diagnosticResultText.text = "Running network diagnostics..."
        
        CoroutineScope(Dispatchers.IO).launch {
            val results = StringBuilder()
            
            // Check internet connectivity
            results.append("Internet connectivity: ${if (isNetworkAvailable()) "AVAILABLE" else "NOT AVAILABLE"}\n\n")
            
            // Get current API endpoint
            results.append("Current API endpoint: ${ApiService.BASE_URL}\n\n")
            
            // Try to ping the server
            try {
                val runtime = Runtime.getRuntime()
                val ipAddress = ApiService.BASE_URL.replace("http://", "").replace("https://", "").split("/")[0]
                val pingCmd = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    runtime.exec("ping -c 3 $ipAddress")
                } else {
                    runtime.exec("ping -c 3 -W 5 $ipAddress")
                }
                
                val inputStream = pingCmd.inputStream
                val reader = inputStream.bufferedReader()
                val pingResult = reader.readText()
                
                results.append("Ping test result:\n$pingResult\n\n")
            } catch (e: Exception) {
                results.append("Ping test failed: ${e.message}\n\n")
            }
            
            // Display results
            withContext(Dispatchers.Main) {
                binding.diagnosticResultText.text = results.toString()
            }
        }
    }
}