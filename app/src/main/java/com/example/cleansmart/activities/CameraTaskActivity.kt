package com.example.cleansmart.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansmart.R
import com.example.cleansmart.adapters.TaskAdapter
import com.example.cleansmart.databinding.ActivityCameraTaskBinding
import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskDataTransfer
import com.example.cleansmart.viewmodels.TaskGenerationViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraTaskBinding
    private val viewModel: TaskGenerationViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    
    private val TAG = "CameraTaskActivity"
    private var currentTasks: List<Task> = emptyList()
    private var isSelectAllChecked = false

    companion object {
        const val RESULT_TASK_DATA = "result_task_data"
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                displayCapturedImage(imageBitmap)
                viewModel.setCapturedImage(imageBitmap)
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            context = this,
            tasks = emptyList(),
            onTaskClicked = { task, isSelected ->
                // Update the selection state in the UI and enable/disable the Add button
                updateAddButtonState()
                // Show toast for better feedback (optional)
                if (isSelected) {
                    Toast.makeText(this, "${task.title} selected", Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        binding.tasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CameraTaskActivity)
            adapter = taskAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.captureButton.setOnClickListener {
            checkCameraPermissionAndOpen()
        }
        
        binding.generateTasksButton.setOnClickListener {
            val areaName = binding.areaNameInput.text.toString().trim()
            
            if (areaName.isEmpty()) {
                Toast.makeText(this, "Please enter an area name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            viewModel.setAreaName(areaName)
            
            // In a real app, we'd call the API, but for demo purposes we'll use predefined tasks
            viewModel.generatePredefinedTasks()
            // If you want to test the API integration:
            // viewModel.generateTasksFromApi()
        }

        binding.addToDashboardButton.setOnClickListener {
            if (taskAdapter.hasSelectedTasks()) {
                addSelectedTasksToDashboard()
            } else {
                Toast.makeText(this, "Please select at least one task", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.selectAllCheckbox.setOnClickListener {
            isSelectAllChecked = binding.selectAllCheckbox.isChecked
            taskAdapter.selectAllTasks(isSelectAllChecked)
            updateAddButtonState()
        }
    }
    
    private fun updateAddButtonState() {
        // Get number of selected tasks
        val selectedCount = taskAdapter.getSelectedTasks().size
        
        // Enable or disable the Add to Dashboard button based on selections
        val hasSelections = selectedCount > 0
        binding.addToDashboardButton.isEnabled = hasSelections
        
        // Update button text to show selection count
        if (hasSelections) {
            binding.addToDashboardButton.text = "Add $selectedCount Task${if (selectedCount > 1) "s" else ""} to Dashboard"
        } else {
            binding.addToDashboardButton.text = "Add Selected Tasks to Dashboard"
        }
    }
    
    private fun observeViewModel() {
        viewModel.capturedImage.observe(this) { bitmap ->
            bitmap?.let { displayCapturedImage(it) }
        }
        
        viewModel.generatedTasks.observe(this) { tasks ->
            currentTasks = tasks
            showTasks(tasks)
            
            // Reset select all state
            isSelectAllChecked = false
            binding.selectAllCheckbox.isChecked = false
            
            // Show the task selection section when tasks are generated
            binding.taskSelectionLayout.visibility = if (tasks.isNotEmpty()) View.VISIBLE else View.GONE
            
            // Initially the add button is disabled until tasks are selected
            binding.addToDashboardButton.isEnabled = false
        }
        
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.areaName.observe(this) { area ->
            // Update the area name in the UI if needed
        }
    }
    
    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error opening camera: ${e.message}")
        }
    }
    
    private fun displayCapturedImage(bitmap: Bitmap) {
        binding.capturedImageView.setImageBitmap(bitmap)
        binding.capturedImageView.visibility = View.VISIBLE
        binding.noImageText.visibility = View.GONE
    }
    
    private fun showTasks(tasks: List<Task>) {
        if (tasks.isNotEmpty()) {
            binding.tasksHeading.visibility = View.VISIBLE
            binding.tasksRecyclerView.visibility = View.VISIBLE
            taskAdapter.updateTasks(tasks)
        } else {
            binding.tasksHeading.visibility = View.GONE
            binding.tasksRecyclerView.visibility = View.GONE
        }
    }

    private fun addSelectedTasksToDashboard() {
        val areaName = viewModel.areaName.value ?: "Unknown Area"
        
        // Get only the selected tasks
        val selectedTasks = taskAdapter.getSelectedTasks()
        
        // Get the task titles from selected tasks only
        val taskTitles = selectedTasks.map { it.title }
        
        // Get the captured image and convert to Base64
        val imageBase64 = viewModel.capturedImage.value?.let { 
            TaskDataTransfer.convertBitmapToBase64(it)
        }
        
        // Create the data transfer object with only selected tasks
        val taskData = TaskDataTransfer(
            areaName = areaName,
            numberOfTasks = selectedTasks.size,
            taskTitles = taskTitles,
            imageBase64 = imageBase64
        )
        
        // Create intent with task data
        val resultIntent = Intent().apply {
            putExtra(RESULT_TASK_DATA, taskData)
        }
        
        // Set the result and finish
        setResult(Activity.RESULT_OK, resultIntent)
        
        Toast.makeText(this, "${selectedTasks.size} tasks added to dashboard!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 