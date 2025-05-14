package com.example.cleansmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleansmart.R

class TaskDetailAdapter(private val tasks: List<String>) : 
    RecyclerView.Adapter<TaskDetailAdapter.TaskDetailViewHolder>() {

    class TaskDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskText: TextView = view.findViewById(R.id.taskText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_detail, parent, false)
        return TaskDetailViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskDetailViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskText.text = "${position + 1}. $task"
    }
} 