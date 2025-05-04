package com.example.cleansmart.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object UIUtils {
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration).show()
    }

    fun showErrorSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, message, duration)
            .setBackgroundTint(android.graphics.Color.RED)
            .show()
    }

    fun showSuccessSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration)
            .setBackgroundTint(android.graphics.Color.GREEN)
            .show()
    }
} 