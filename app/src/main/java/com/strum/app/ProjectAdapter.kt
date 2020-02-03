package com.strum.app

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_lay.view.*
import java.util.*


class ProjectAdapter(var context: Context, var list: List<ProjectModel>): RecyclerView.Adapter<ProjectAdapter.MyHolder>() {

    var bckListDefault =  Arrays.asList(R.drawable.back_grad, R.drawable.back_grad_a, R.drawable.back_grad_b, R.drawable.back_grad_sec)


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val projectShortTitle = itemView.findViewById(R.id.projectShortTitle) as TextView
        val projectFullTitle = itemView.findViewById(R.id.projectFullTitle) as TextView
        val projectAdmin = itemView.findViewById(R.id.projectAdmin) as TextView
        val projectCover = itemView.findViewById(R.id.coverImgId) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.project_lay, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.projectShortTitle.text = list[position].projSName
        holder.projectFullTitle.text = list[position].projFName
        holder.projectAdmin.text = list[position].projectAdmin
        holder.projectCover.setImageResource(bckListDefault.random())
    }
}