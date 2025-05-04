package com.example.cleansmart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TeamMemberAdapter(
    private val context: Context,
    private val teamMembers: List<AboutDevsActivity.TeamMember>
) : RecyclerView.Adapter<TeamMemberAdapter.TeamMemberViewHolder>() {

    class TeamMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberPhoto: ImageView = itemView.findViewById(R.id.member_photo)
        val memberName: TextView = itemView.findViewById(R.id.member_name)
        val memberTitle: TextView = itemView.findViewById(R.id.member_title)
        val memberBio: TextView = itemView.findViewById(R.id.member_bio)
        val funFactText: TextView = itemView.findViewById(R.id.fun_fact_text)
        val skillsContainer: LinearLayout = itemView.findViewById(R.id.skills_container)
        val memberHeader: ConstraintLayout = itemView.findViewById(R.id.member_header)
        val memberContent: LinearLayout = itemView.findViewById(R.id.member_content)
        val expandIcon: ImageView = itemView.findViewById(R.id.expand_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team_member, parent, false)
        return TeamMemberViewHolder(view)
    }

    override fun getItemCount(): Int = teamMembers.size

    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        val member = teamMembers[position]

        // Set basic info
        holder.memberName.text = member.name
        holder.memberTitle.text = member.title
        holder.memberBio.text = member.bio
        holder.funFactText.text = member.funFact
        holder.memberPhoto.setImageResource(member.photoResId)

        // Setup skills tags
        setupSkillTags(holder, member)

        // Setup expandable functionality
        setupExpansion(holder)
    }

    private fun setupSkillTags(holder: TeamMemberViewHolder, member: AboutDevsActivity.TeamMember) {
        holder.skillsContainer.removeAllViews() // Clear default tags

        // Add skill tags dynamically
        for (skill in member.skills) {
            val skillTag = TextView(context)
            skillTag.text = skill
            skillTag.setTextColor(ContextCompat.getColor(context, R.color.text_accent_color))
            skillTag.background = ContextCompat.getDrawable(context, R.drawable.skill_tag_background)
            skillTag.setPadding(
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_padding_vertical),
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_padding_vertical)
            )

            // Set layout params with margins
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(
                0, 0,
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_margin_end),
                context.resources.getDimensionPixelSize(R.dimen.skill_tag_margin_bottom)
            )
            skillTag.layoutParams = layoutParams

            // Add to container
            holder.skillsContainer.addView(skillTag)
        }
    }

    private fun setupExpansion(holder: TeamMemberViewHolder) {
        holder.memberHeader.setOnClickListener {
            // Toggle visibility of content
            val isVisible = holder.memberContent.visibility == View.VISIBLE
            holder.memberContent.visibility = if (isVisible) View.GONE else View.VISIBLE

            // Rotate icon based on expanded state
            holder.expandIcon.rotation = if (isVisible) 0f else 180f
        }
    }
}