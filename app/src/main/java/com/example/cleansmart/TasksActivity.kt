package com.example.cleansmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cleansmart.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
} 