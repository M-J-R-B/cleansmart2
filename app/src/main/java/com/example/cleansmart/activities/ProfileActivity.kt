package com.example.cleansmart.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivityProfileBinding
import com.google.android.material.appbar.AppBarLayout

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupClickListeners() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / scrollRange.toFloat()

            // Fade out the profile image and name as user scrolls
            binding.profileImage.alpha = 1 - percentage
        })

        binding.editButton.setOnClickListener {
            try {
                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error opening edit profile screen", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changePasswordButton.setOnClickListener {
            try {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error opening change password screen", Toast.LENGTH_SHORT).show()
            }
        }
    }
}