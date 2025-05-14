package com.cleansmart.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.R
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
        
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / scrollRange.toFloat()
            
            // Fade out the profile image and name as user scrolls
            binding.profileImage.alpha = 1 - percentage
        })
    }
    
    private fun setupClickListeners() {
        binding.editButton.setOnClickListener {
            // TODO: Implement edit profile functionality
        }
        
        binding.changePasswordButton.setOnClickListener {
            // TODO: Implement settings functionality
        }
        
//        binding.backButton.setOnClickListener {
//            onBackPressed()
//        }
    }
} 