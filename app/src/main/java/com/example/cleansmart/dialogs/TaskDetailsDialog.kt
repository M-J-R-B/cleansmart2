package com.example.cleansmart.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansmart.R
import com.example.cleansmart.adapters.TaskDetailAdapter
import com.example.cleansmart.databinding.DialogTaskDetailsBinding
import com.example.cleansmart.models.TaskDataTransfer

class TaskDetailsDialog(
    context: Context,
    private val taskData: TaskDataTransfer
) : Dialog(context) {

    private lateinit var binding: DialogTaskDetailsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = DialogTaskDetailsBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        
        // Set dialog dimensions
        window?.setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )
        
        setupViews()
    }
    
    private fun setupViews() {
        // Set the dialog title
        binding.dialogTitle.text = "${taskData.areaName} Tasks"
        
        // Display the image if available
        val image = TaskDataTransfer.convertBase64ToBitmap(taskData.imageBase64)
        if (image != null) {
            binding.areaImageView.setImageBitmap(image)
            binding.areaImageView.visibility = View.VISIBLE
            binding.noImageText.visibility = View.GONE
        } else {
            binding.areaImageView.visibility = View.GONE
            binding.noImageText.visibility = View.VISIBLE
        }
        
        // Set up the RecyclerView for tasks
        binding.taskDetailsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskDetailAdapter(taskData.taskTitles)
        }
        
        // Set up the close button
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
} 