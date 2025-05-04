package com.example.cleansmart

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListViewDemoActivity : AppCompatActivity() {
    private lateinit var simpleListView: ListView
    private lateinit var customListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_demo)

        // Initialize ListViews
        simpleListView = findViewById(R.id.simpleListView)
        customListView = findViewById(R.id.customListView)

        setupSimpleListView()
        setupCustomListView()
    }

    private fun setupSimpleListView() {
        // Sample data for simple list
        val items = arrayOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5"
        )

        // Create simple adapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )

        simpleListView.adapter = adapter

        // Handle item clicks
        simpleListView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicked: ${items[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCustomListView() {
        // Sample data for custom list
        val customItems = listOf(
            CustomListItem("Task 1", "High Priority", true),
            CustomListItem("Task 2", "Medium Priority", false),
            CustomListItem("Task 3", "Low Priority", true),
            CustomListItem("Task 4", "High Priority", false),
            CustomListItem("Task 5", "Medium Priority", true)
        )

        // Create custom adapter
        val customAdapter = CustomListViewAdapter(this, customItems)
        customListView.adapter = customAdapter

        // Handle item clicks
        customListView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicked: ${customItems[position].title}", Toast.LENGTH_SHORT).show()
        }
    }
}

// Data class for custom list items
data class CustomListItem(
    val title: String,
    val priority: String,
    val isCompleted: Boolean
)

// Custom adapter for the custom ListView
class CustomListViewAdapter(
    private val context: android.content.Context,
    private val items: List<CustomListItem>
) : android.widget.BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup?): android.view.View {
        val view = convertView ?: android.view.LayoutInflater.from(context)
            .inflate(R.layout.item_custom_list, parent, false)

        val item = items[position]

        // Set title
        view.findViewById<TextView>(R.id.itemTitle).text = item.title
        
        // Set priority with color
        val priorityText = view.findViewById<TextView>(R.id.itemPriority)
        priorityText.text = item.priority
        priorityText.setTextColor(
            when (item.priority) {
                "High Priority" -> android.graphics.Color.RED
                "Medium Priority" -> android.graphics.Color.rgb(255, 165, 0) // Orange
                else -> android.graphics.Color.GREEN
            }
        )

        // Set completion status
        view.findViewById<TextView>(R.id.itemStatus).text = 
            if (item.isCompleted) "✓ Completed" else "○ Pending"

        return view
    }
} 