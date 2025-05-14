package com.example.cleansmart.models

import android.graphics.Bitmap
import android.os.Parcelable
import android.util.Base64
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayOutputStream
import java.util.UUID
import java.util.Date

@Parcelize
data class TaskDataTransfer(
    val id: String = UUID.randomUUID().toString(),
    val areaName: String,
    val numberOfTasks: Int,
    val taskTitles: List<String>,
    val imageBase64: String? = null,
    val dateCreated: Long = Date().time
) : Parcelable {
    companion object {
        fun convertBitmapToBase64(bitmap: Bitmap?): String? {
            if (bitmap == null) return null
            return try {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                val byteArray = outputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            } catch (e: Exception) {
                null
            }
        }
        
        fun convertBase64ToBitmap(base64String: String?): Bitmap? {
            if (base64String == null) return null
            return try {
                val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: Exception) {
                null
            }
        }
    }
} 