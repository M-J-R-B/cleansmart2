package com.example.cleansmart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.cleansmart.utils.SecureStorageManager

class LogoutDialogFragment : DialogFragment() {

    interface LogoutDialogListener {
        fun onLogoutConfirmed()
        fun onLogoutCancelled()
    }

    private var listener: LogoutDialogListener? = null

    fun setLogoutDialogListener(listener: LogoutDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_logout_confirmation, null)

            // Setup dialog buttons
            val logoutButton = view.findViewById<Button>(R.id.logoutButton)
            val saveLogoutButton = view.findViewById<Button>(R.id.saveLogoutButton)
            val cancelButton = view.findViewById<Button>(R.id.cancelButton)

            logoutButton.setOnClickListener {
                performLogout()
            }

            saveLogoutButton.setOnClickListener {
                performLogout()
            }

            cancelButton.setOnClickListener {
                listener?.onLogoutCancelled()
                dismiss()
            }

            builder.setView(view)
                .setCancelable(false)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun performLogout() {
        activity?.let { act ->
            // Clear all user data first
            val secureStorage = SecureStorageManager.getInstance(act)
            
            // Clear all shared preferences
            act.getSharedPreferences("app_settings", Context.MODE_PRIVATE).edit().clear().apply()
            secureStorage.clearAll()

            // Notify listener before starting new activity
            listener?.onLogoutConfirmed()

            // Create and start login activity
            val loginIntent = Intent(act, SignInActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            
            // Dismiss dialog before starting activity
            dismiss()
            
            // Start login activity and finish all activities
            act.startActivity(loginIntent)
            act.finishAffinity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}