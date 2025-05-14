package com.example.cleansmart

import android.app.Application
import com.example.cleansmart.network.NetworkClient

class CleanSmartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize NetworkClient with application context
        NetworkClient.initialize(applicationContext)
    }
} 