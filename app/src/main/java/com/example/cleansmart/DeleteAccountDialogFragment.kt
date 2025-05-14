package com.example.cleansmart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.DeleteAccountRequest
import com.example.cleansmart.utils.SecureStorageManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class DeleteAccountDialogFragment : DialogFragment() {
    private val TAG = "DeleteAccountDialog"

    interface DeleteAccountDialogListener {
        fun onDeleteAccountConfirmed()
        fun onDeleteAccountCancelled()
    }

    private var listener: DeleteAccountDialogListener? = null
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmationInput: TextInputEditText

    fun setDeleteAccountDialogListener(listener: DeleteAccountDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_delete_account_confirmation, null)

            // Get references to input fields
            emailInput = view.findViewById(R.id.emailInput)
            passwordInput = view.findViewById(R.id.passwordInput)
            confirmationInput = view.findViewById(R.id.confirmationInput)

            // Prefill email if available
            val secureStorage = SecureStorageManager.getInstance(it)
            secureStorage.getEmail()?.let { email ->
                emailInput.setText(email)
            }

            // Setup dialog buttons
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)
            val cancelButton = view.findViewById<Button>(R.id.cancelButton)

            deleteButton.setOnClickListener {
                if (validateInputs()) {
                    performDeleteAccount()
                }
            }

            cancelButton.setOnClickListener {
                listener?.onDeleteAccountCancelled()
                dismiss()
            }

            builder.setView(view)
                .setCancelable(false)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun validateInputs(): Boolean {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmation = confirmationInput.text.toString()

        if (TextUtils.isEmpty(email)) {
            emailInput.error = "Email is required"
            return false
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.error = "Password is required"
            return false
        }

        if (confirmation != "DELETE") {
            confirmationInput.error = "Type DELETE to confirm"
            return false
        }

        return true
    }

    private fun performDeleteAccount() {
        activity?.let { act ->
            // Get values from input fields
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmation = confirmationInput.text.toString()
            
            // Show loading state
            val loadingToast = Toast.makeText(act, "Deleting account...", Toast.LENGTH_LONG)
            loadingToast.show()
            
            // Make API call to delete account
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService = ApiService.create()
                    val deleteRequest = DeleteAccountRequest(
                        email = email,
                        password = password,
                        confirmation = confirmation
                    )
                    val response = apiService.deleteAccount(deleteRequest)
                    
                    withContext(Dispatchers.Main) {
                        loadingToast.cancel()
                        
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(act, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                            
                            // Delete profile image file
                            deleteProfileImage(act)
                            
                            // Clear all user data
                            val secureStorage = SecureStorageManager.getInstance(act)
                            secureStorage.clearAll()
                            
                            // Clear shared preferences
                            act.getSharedPreferences("app_settings", Context.MODE_PRIVATE).edit().clear().apply()
                            act.getSharedPreferences("user_session", Context.MODE_PRIVATE).edit().clear().apply()
                            
                            // Notify listener
                            listener?.onDeleteAccountConfirmed()
                            
                            // Redirect to sign in screen
                            val signInIntent = Intent(act, SignInActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                            
                            // Dismiss dialog before starting activity
                            dismiss()
                            
                            // Start activity and finish all activities
                            act.startActivity(signInIntent)
                            act.finishAffinity()
                        } else {
                            val errorMsg = response.body()?.message ?: "Failed to delete account"
                            Toast.makeText(act, errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        loadingToast.cancel()
                        Toast.makeText(act, "Error: ${e.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    
    private fun deleteProfileImage(context: Context) {
        try {
            // Get the profile image path from shared preferences
            val sharedPrefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val profileImagePath = sharedPrefs.getString("profileImagePath", null)
            
            // Delete the actual file if it exists
            if (profileImagePath != null) {
                val file = File(profileImagePath)
                if (file.exists()) {
                    val deleted = file.delete()
                    Log.d(TAG, "Profile image deleted: $deleted")
                }
            }
            
            // Delete the entire profile_images directory
            val imagesDir = File(context.filesDir, "profile_images")
            if (imagesDir.exists()) {
                imagesDir.deleteRecursively()
                Log.d(TAG, "Profile images directory deleted")
            }
            
            // Clear the path from preferences
            sharedPrefs.edit().remove("profileImagePath").apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting profile image: ${e.message}", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
} 