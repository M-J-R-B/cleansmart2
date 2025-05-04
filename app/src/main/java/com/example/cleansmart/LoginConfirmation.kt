package com.example.cleansmart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class LoginConfirmation : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_confirmation)
        
        // Show logout message
        Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show()

        // Delay and finish this activity
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 2000)
    }

    override fun onBackPressed() {
        // Prevent going back
        return
    }
}