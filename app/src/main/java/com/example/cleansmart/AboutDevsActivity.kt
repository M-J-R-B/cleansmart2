package com.example.cleansmart

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AboutDevsActivity : FragmentActivity() {

    // Data class for team member information
    data class TeamMember(
        val name: String,
        val title: String,
        val bio: String,
        val skills: List<String>,
        val funFact: String,
        val photoResId: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_devs)


        // Sample team member data
        val teamMembers = listOf(
            TeamMember(
                "Mary Jhoy R. Baselisco",
                "Lead Developer",
                "MJ brings 8+ years of experience in full-stack development and has a passion for creating scalable, user-friendly applications.",
                listOf("React", "Node.js", "AWS", "Architecture"),
                "MJ once coded for 72 hours straight to win a hackathon.",
                R.drawable.profile_mj
            ),
            TeamMember(
                "Hermelyn C. Becher",
                "UX/UI Designer",
                "Hermelyn creates beautiful and intuitive user experiences. With a background in both design and psychology, they bring a unique perspective to every project.",
                listOf("Figma", "User Research", "UI Design", "Prototyping"),
                "Hermelyn has a collection of over 200 different fonts.",
                R.drawable.profile_herme
            ),
            TeamMember(
                "John Laurence C. Codeniera",
                "Backend Developer",
                "Laurence specializes in building robust backend systems. With deep expertise in database optimization and API design, they ensure our applications run smoothly.",
                listOf("Python", "Go", "SQL", "Docker"),
                "Laurence built their first computer at age 12.",
                R.drawable.profile_laurence
            )
        )

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.team_members_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TeamMemberAdapter(this, teamMembers)
    }
}