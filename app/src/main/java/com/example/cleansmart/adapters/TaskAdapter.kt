package com.example.cleansmart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cleansmart.R
import com.example.cleansmart.models.Priority
import com.example.cleansmart.models.Task

class TaskAdapter(
    private val context: Context,
    private var tasks: List<Task>,
    private val onTaskClicked: (Task, Boolean) -> Unit,
    private val onTaskLongPressed: (Task) -> Boolean = { false } // Default implementation returns false
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Track selected tasks
    private val selectedTasks = mutableSetOf<String>() // Store task IDs
    
    // Get the list of selected tasks
    fun getSelectedTasks(): List<Task> {
        return tasks.filter { selectedTasks.contains(it.id) }
    }
    
    // Check if any tasks are selected
    fun hasSelectedTasks(): Boolean {
        return selectedTasks.isNotEmpty()
    }
    
    // Select or deselect all tasks
    fun selectAllTasks(select: Boolean) {
        if (select) {
            tasks.forEach { selectedTasks.add(it.id) }
        } else {
            selectedTasks.clear()
        }
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskArea: TextView = itemView.findViewById(R.id.taskArea)
        val taskPriorityIndicator: ImageView = itemView.findViewById(R.id.priorityIndicator)
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.taskCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description
        holder.taskArea.text = task.area
        
        // In TasksActivity (viewing/completing tasks mode)
        if (context.javaClass.simpleName.contains("TasksActivity")) {
            // Use task's isCompleted property for the checkbox
            holder.taskCheckbox.isChecked = task.isCompleted
        } else {
            // In CameraTaskActivity (task selection mode)
            // Check if this task is currently selected
            holder.taskCheckbox.isChecked = selectedTasks.contains(task.id)
        }
        
        // Apply strikethrough to completed tasks
        if (task.isCompleted) {
            holder.taskTitle.apply {
                paintFlags = paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                alpha = 0.7f
            }
            holder.taskDescription.apply {
                paintFlags = paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                alpha = 0.7f
            }
        } else {
            holder.taskTitle.apply {
                paintFlags = paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                alpha = 1.0f
            }
            holder.taskDescription.apply {
                paintFlags = paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                alpha = 1.0f
            }
        }
        
        // Set priority indicator color
        val priorityColor = when (task.priority) {
            Priority.HIGH -> ContextCompat.getColor(context, R.color.priority_high)
            Priority.MEDIUM -> ContextCompat.getColor(context, R.color.priority_medium)
            Priority.LOW -> ContextCompat.getColor(context, R.color.priority_low)
        }
        holder.taskPriorityIndicator.setColorFilter(priorityColor)
        
        // Clear any existing listeners to prevent double registration
        holder.taskCheckbox.setOnCheckedChangeListener(null)
        
        // Determine if we're in task completion mode or selection mode
        val isTaskCompletionMode = context.javaClass.simpleName.contains("TasksActivity")
        
        // Set item click listener (for card click)
        holder.itemView.setOnClickListener {
            if (isTaskCompletionMode) {
                // In TasksActivity - toggle completion status
                val newTask = task.copy(isCompleted = !task.isCompleted)
                onTaskClicked(newTask, true)
            } else {
                // In CameraTaskActivity - toggle selection
                if (selectedTasks.contains(task.id)) {
                    selectedTasks.remove(task.id)
                } else {
                    selectedTasks.add(task.id)
                }
                notifyItemChanged(position)
                onTaskClicked(task, selectedTasks.contains(task.id))
            }
        }
        
        // Set long click listener for deletion
        holder.itemView.setOnLongClickListener {
            onTaskLongPressed(task)
        }
        
        // Set checkbox click listener
        holder.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isTaskCompletionMode) {
                // In TasksActivity - update completion status
                val updatedTask = task.copy(isCompleted = isChecked)
                onTaskClicked(updatedTask, true)
            } else {
                // In CameraTaskActivity - update selection
                if (isChecked) {
                    selectedTasks.add(task.id)
                } else {
                    selectedTasks.remove(task.id)
                }
                onTaskClicked(task, isChecked)
            }
        }
    }
    
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
} 