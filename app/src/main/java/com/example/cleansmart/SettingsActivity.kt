package com.example.cleansmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

class SettingsActivity : FragmentActivity(), LogoutDialogFragment.LogoutDialogListener {

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

    override fun onLogoutConfirmed() {
        // The actual logout is now handled in LogoutDialogFragment
    }

    override fun onLogoutCancelled() {
        Toast.makeText(this, "Logout cancelled", Toast.LENGTH_SHORT).show()
    }
}