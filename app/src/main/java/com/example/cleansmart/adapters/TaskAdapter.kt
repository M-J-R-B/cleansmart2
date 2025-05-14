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
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

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
        holder.taskCheckbox.isChecked = task.isCompleted
        
        // Set priority indicator color
        val priorityColor = when (task.priority) {
            Priority.HIGH -> ContextCompat.getColor(context, R.color.priority_high)
            Priority.MEDIUM -> ContextCompat.getColor(context, R.color.priority_medium)
            Priority.LOW -> ContextCompat.getColor(context, R.color.priority_low)
        }
        holder.taskPriorityIndicator.setColorFilter(priorityColor)
        
        // Set item click listener
        holder.itemView.setOnClickListener {
            onTaskClicked(task)
        }
        
        // Set checkbox click listener
        holder.taskCheckbox.setOnClickListener {
            // To prevent triggering both listeners, we stop the event propagation
            it.isClickable = false
            onTaskClicked(task.copy(isCompleted = holder.taskCheckbox.isChecked))
            it.isClickable = true
        }
    }
    
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
} 