package com.strum.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.team_memb_lay.view.*


class TeamAdapter(var context: Context, var teamMembList: List<TeamMemberModel>, var origMembCount: Int): RecyclerView.Adapter<TeamAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.team_memb_lay, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return teamMembList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if(position<3){
            Picasso.with(context).load(teamMembList[position].url).into(holder.itemView.teamMembPic)
        }
        else
            holder.itemView.teammembNumber.text = "+"+(origMembCount-3)
    }

}