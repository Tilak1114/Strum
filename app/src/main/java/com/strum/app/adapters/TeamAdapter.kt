package com.strum.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.strum.app.R
import com.strum.app.models.TeamMemberModel
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
        if(teamMembList[position].url!="END"){
            //Picasso.with(context).load("https://www.google.com/imgres?imgurl=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D1782996298392336&imgrefurl=https%3A%2F%2Fwww.facebook.com%2FMia-Khalifa-fans-1782996298392336%2F&tbnid=6a1G6QUobCnHRM&vet=12ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ..i&docid=bY6Z5N2Ct2ufgM&w=566&h=600&q=mia%20khalifa%20profile%20pic&ved=2ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ").into(holder.itemView.teamMembPic)
            Picasso.with(context).load(teamMembList[position].url).into(holder.itemView.teamMembPic)
        }
        else{
            holder.itemView.teammembNumber.visibility = View.VISIBLE
            holder.itemView.teammembNumber.text = "+"+(origMembCount-3)
        }
    }

}