package com.strum.app.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.strum.app.R
import com.strum.app.models.TeamMemberModel
import kotlinx.android.synthetic.main.assignee_lay.view.*


class AssigneeAdapter(var context: Context, var teammemblist:List<TeamMemberModel>, var assigneeSelection: AssigneeSelection): RecyclerView.Adapter<AssigneeAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface AssigneeSelection{
        fun onSelected(userId: Int)
    }

    var selUserId = -1
    var currentSel = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.assignee_lay, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return teammemblist.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val drawable: Drawable? = context.getDrawable(R.drawable.circlefull)
        var drawablesel: Drawable? = context.getDrawable(R.drawable.circlesel)

        Picasso.with(context).load(teammemblist[position].url).into(holder.itemView.teamMembPicAssignee)

        holder.itemView.assigneeName.text = teammemblist[position].userName

        holder.itemView.setOnClickListener{
            selUserId = teammemblist[position].userId
        }

        if(currentSel==position){
            holder.itemView.teamMembPicAssignee.borderWidth = 10
            holder.itemView.teamMembPicAssignee.borderColor = ContextCompat.getColor(context,
                R.color.workColor
            )
        }

        else{
            holder.itemView.teamMembPicAssignee.borderWidth = 0
            holder.itemView.teamMembPicAssignee.borderColor = ContextCompat.getColor(context, android.R.color.white)
        }

        holder.itemView.setOnClickListener {
            currentSel = position
            selUserId = position
            notifyDataSetChanged()
            assigneeSelection.onSelected(teammemblist[currentSel].userId)
        }
    }

    fun getSelItem(): Int{
        return selUserId
    }
}