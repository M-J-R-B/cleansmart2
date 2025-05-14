package com.example.cleansmart.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivityChangePasswordBinding
import com.example.cleansmart.viewmodels.ChangePasswordState
import com.example.cleansmart.viewmodels.ChangePasswordViewModel

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.btnChangePassword.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validateInput(oldPassword, newPassword, confirmPassword)) {
                viewModel.changePassword(this, oldPassword, newPassword)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.changePasswordState.observe(this) { state ->
            when (state) {
                is ChangePasswordState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnChangePassword.isEnabled = false
                }
                is ChangePasswordState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnChangePassword.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    finish()
                }
                is ChangePasswordState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnChangePassword.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateInput(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {
        if (oldPassword.isEmpty()) {
            binding.etOldPassword.error = "Please enter your current password"
            return false
        }

        if (newPassword.isEmpty()) {
            binding.etNewPassword.error = "Please enter a new password"
            return false
        }

        if (newPassword.length < 6) {
            binding.etNewPassword.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Please confirm your new password"
            return false
        }

        if (newPassword != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }

        return true
    }
} 