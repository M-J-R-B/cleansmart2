package com.example.cleansmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AlertDialog

class SettingsActivity : FragmentActivity(), LogoutDialogFragment.LogoutDialogListener, DeleteAccountDialogFragment.DeleteAccountDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Setup back button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Get saved preferences
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)

        // Setup setting items click listeners
        setupSettingsItemClickListeners()
    }

    private fun setupSettingsItemClickListeners() {
        // API Endpoint settings
        findViewById<LinearLayout>(R.id.apiEndpointLayout).setOnClickListener {
            showApiEndpointDialog()
        }

        // Account settings
        findViewById<LinearLayout>(R.id.accountLayout).setOnClickListener {
            Toast.makeText(this, "Account settings", Toast.LENGTH_SHORT).show()
        }

        // Notifications settings
        findViewById<LinearLayout>(R.id.notificationsLayout).setOnClickListener {
            Toast.makeText(this, "Notifications Settings", Toast.LENGTH_SHORT).show()
        }

        // Dark mode settings
        findViewById<LinearLayout>(R.id.darkModeLayout).setOnClickListener {
            Toast.makeText(this, "Dark Mode Settings", Toast.LENGTH_SHORT).show()
        }

        // Language settings
        findViewById<LinearLayout>(R.id.languageLayout).setOnClickListener {
            Toast.makeText(this, "Language settings", Toast.LENGTH_SHORT).show()
        }

        // Security settings
        findViewById<LinearLayout>(R.id.securityLayout).setOnClickListener {
            Toast.makeText(this, "Security settings", Toast.LENGTH_SHORT).show()
        }

        // Terms and conditions
        findViewById<LinearLayout>(R.id.termsLayout).setOnClickListener {
            Toast.makeText(this, "Terms & Conditions", Toast.LENGTH_SHORT).show()
        }

        // Privacy policy
        findViewById<LinearLayout>(R.id.privacyLayout).setOnClickListener {
            Toast.makeText(this, "Privacy Policy", Toast.LENGTH_SHORT).show()
        }

        // About the developers
        findViewById<LinearLayout>(R.id.aboutLayout).setOnClickListener {
            Toast.makeText(this, "About the Developers", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AboutDevsActivity::class.java)
            startActivity(intent)
        }

        // ListView Demo
        findViewById<LinearLayout>(R.id.listViewDemoLayout).setOnClickListener {
            val intent = Intent(this, ListViewDemoActivity::class.java)
            startActivity(intent)
        }

        // Invite a friend
        findViewById<LinearLayout>(R.id.inviteLayout).setOnClickListener {
            Toast.makeText(this, "Invite a friend", Toast.LENGTH_SHORT).show()
            // Create share intent
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
                putExtra(Intent.EXTRA_TEXT, "I've been using CleanSmart for my cleaning tasks. You should try it too!")
            }
            startActivity(Intent.createChooser(intent, "Share via"))
        }
        
        // Delete Account
        findViewById<LinearLayout>(R.id.deleteAccountLayout).setOnClickListener {
            showDeleteAccountConfirmationDialog()
        }

        // Logout
        findViewById<LinearLayout>(R.id.logoutLayout).setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val logoutDialog = LogoutDialogFragment()
        logoutDialog.setLogoutDialogListener(this)
        logoutDialog.show(supportFragmentManager, "LogoutDialog")
    }
    
    private fun showDeleteAccountConfirmationDialog() {
        val deleteAccountDialog = DeleteAccountDialogFragment()
        deleteAccountDialog.setDeleteAccountDialogListener(this)
        deleteAccountDialog.show(supportFragmentManager, "DeleteAccountDialog")
    }

    override fun onLogoutConfirmed() {
        // The actual logout is now handled in LogoutDialogFragment
    }

    override fun onLogoutCancelled() {
        Toast.makeText(this, "Logout cancelled", Toast.LENGTH_SHORT).show()
    }
    
    override fun onDeleteAccountConfirmed() {
        // The actual account deletion is handled in DeleteAccountDialogFragment
    }
    
    override fun onDeleteAccountCancelled() {
        Toast.makeText(this, "Account deletion cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun showApiEndpointDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select API Endpoint")
        
        val endpoints = arrayOf(
            "Development (Local IP: 192.168.1.9)", 
            "Emulator (10.0.2.2)", 
            "Production"
        )
        
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val currentEndpoint = sharedPrefs.getInt("api_endpoint", 0)
        
        builder.setSingleChoiceItems(endpoints, currentEndpoint) { dialog, which ->
            // Save the selected endpoint
            sharedPrefs.edit().putInt("api_endpoint", which).apply()
            
            // Update the description
            val apiEndpointDescription = findViewById<android.widget.TextView>(R.id.apiEndpointDescription)
            apiEndpointDescription.text = "Using: ${endpoints[which]}"
            
            // Show a message that app restart is required
            Toast.makeText(this, "Restart app to apply new API endpoint", Toast.LENGTH_LONG).show()
            
            dialog.dismiss()
        }
        
        val dialog = builder.create()
        dialog.show()
    }
}