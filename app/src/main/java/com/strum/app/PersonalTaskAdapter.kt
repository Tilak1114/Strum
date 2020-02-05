package com.strum.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.personal_task_item_lay.view.*
import kotlinx.android.synthetic.main.project_lay.view.*
import java.text.DateFormat
import java.util.*

class PersonalTaskAdapter(var context: Context, var tasks: List<PersonalTaskModel>, var statusChangeListener: StatusChangeListener): RecyclerView.Adapter<PersonalTaskAdapter.MyHolder>() {

    interface StatusChangeListener{
        fun onStatusChanged(status: String, id: Int, priority: String, taskName: String, date: Date)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.personal_task_item_lay, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        when(tasks[position].priority){
           "HIGH"->holder.itemView.priorityBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.prioHigh))
           "MED"->holder.itemView.priorityBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.prioMed))
           "LOW" -> holder.itemView.priorityBadge.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
        }

        holder.itemView.personalDeadline.text = DateFormat.getDateInstance(DateFormat.LONG).format(tasks[position].deadline).toString()
        holder.itemView.persTaskName.text = tasks[position].taskName

//        holder.itemView.projectCard.setOnClickListener{
//            holder.itemView.taskStatusCb.isChecked = !holder.itemView.taskStatusCb.isChecked
//        }

        holder.itemView.taskStatusCb.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                tasks[position].status = "Completed"
                holder.itemView.strikeView.visibility = View.VISIBLE
                statusChangeListener.onStatusChanged("Completed", tasks[position].taskId, tasks[position].priority, tasks[position].taskName, tasks[position].deadline)
                holder.itemView.taskStatusCb.visibility = View.INVISIBLE
            }
//            else{
//                tasks[position].status = "Pending"
//                holder.itemView.strikeView.visibility = View.INVISIBLE
//                statusChangeListener.onStatusChanged("Pending", tasks[position].taskId, tasks[position].priority, tasks[position].taskName, tasks[position].deadline)
//            }
        }

        if(tasks[position].status == "Completed"){
            holder.itemView.taskStatusCb.visibility = View.INVISIBLE
            holder.itemView.strikeView.visibility = View.VISIBLE
        }
        else if(tasks[position].status == "Pending"){
            holder.itemView.taskStatusCb.isChecked = false
            holder.itemView.strikeView.visibility = View.INVISIBLE
        }
    }
}