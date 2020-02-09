package com.strum.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.strum.app.R
import com.strum.app.models.PersonalTaskModel
import com.strum.app.models.WorkTask
import kotlinx.android.synthetic.main.personal_task_item_lay.view.*
import kotlinx.android.synthetic.main.work_task_item_lay.view.*
import java.text.DateFormat
import java.util.*

class WorkTaskAdapter(var context: Context, var tasks: List<WorkTask>, var statusChangeListener: StatusChangeListener): RecyclerView.Adapter<WorkTaskAdapter.MyHolder>() {

    interface StatusChangeListener{
        fun onStatusChanged(status: String, taskid: Int, priority: String, taskName: String, date: String, userid: Int)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.work_task_item_lay, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        when(tasks[position].priority){
           "HIGH"->holder.itemView.priorityBadgework.setBackgroundColor(ContextCompat.getColor(context,
               R.color.prioHigh
           ))
           "MED"->holder.itemView.priorityBadgework.setBackgroundColor(ContextCompat.getColor(context,
               R.color.prioMed
           ))
           "LOW" -> holder.itemView.priorityBadgework.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
        }

        holder.itemView.workdeadline.text = tasks[position].deadline
        holder.itemView.worktaskname.text = tasks[position].taskname

//        holder.itemView.projectCard.setOnClickListener{
//            holder.itemView.taskStatusCb.isChecked = !holder.itemView.taskStatusCb.isChecked
//        }

        holder.itemView.worktaskStatusCb.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                tasks[position].status = "Completed"
                //holder.itemView.strikeView.visibility = View.VISIBLE
                //holder.itemView.taskStatusCb.visibility = View.INVISIBLE
                statusChangeListener.onStatusChanged("Completed", tasks[position].taskid,
                    tasks[position].priority, tasks[position].taskname, tasks[position].deadline, tasks[position].userid)
            }
//            else{
//                tasks[position].status = "Pending"
//                holder.itemView.strikeView.visibility = View.INVISIBLE
//                statusChangeListener.onStatusChanged("Pending", tasks[position].taskId, tasks[position].priority, tasks[position].taskName, tasks[position].deadline)
//            }
        }

        if(tasks[position].status == "Completed"){
            holder.itemView.worktaskStatusCb.visibility = View.INVISIBLE
            holder.itemView.strikeViewwork.visibility = View.VISIBLE
        }
        else if(tasks[position].status == "Pending"){
            holder.itemView.worktaskStatusCb.isChecked = false
            holder.itemView.strikeViewwork.visibility = View.INVISIBLE
        }
    }
}