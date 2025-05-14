package com.example.cleansmart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleansmart.R
import com.example.cleansmart.models.TaskGroup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskGroupAdapter(
    private val context: Context,
    private var taskGroups: List<TaskGroup>,
    private val onTaskGroupClicked: (TaskGroup) -> Unit,
    private val onTaskGroupLongPressed: (TaskGroup) -> Boolean
) : RecyclerView.Adapter<TaskGroupAdapter.TaskGroupViewHolder>() {

    class TaskGroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.dateText)
        val todoTitle: TextView = view.findViewById(R.id.todoTitle)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val progressLabel: TextView = view.findViewById(R.id.progressLabel)
        val timeText: TextView = view.findViewById(R.id.timeText)
        val menuIcon: ImageView = view.findViewById(R.id.menuIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_group, parent, false)
        return TaskGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskGroupViewHolder, position: Int) {
        val taskGroup = taskGroups[position]
        
        // Format the date
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(taskGroup.dateCreated))
        
        holder.dateText.text = formattedDate
        holder.todoTitle.text = "${taskGroup.areaName} Tasks"
        
        // Set progress
        holder.progressBar.progress = taskGroup.progress
        holder.progressLabel.text = "Progress: ${taskGroup.progress}%"
        
        // Set task count
        val taskCount = taskGroup.tasks.size
        holder.timeText.text = "$taskCount tasks to complete"
        
        // Set click listener
        holder.itemView.setOnClickListener {
            onTaskGroupClicked(taskGroup)
        }
        
        // Set long press listener for deletion
        holder.itemView.setOnLongClickListener {
            onTaskGroupLongPressed(taskGroup)
        }
    }

    override fun getItemCount(): Int = taskGroups.size

    fun updateTaskGroups(newTaskGroups: List<TaskGroup>) {
        taskGroups = newTaskGroups
        notifyDataSetChanged()
    }
} 