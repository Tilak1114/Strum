package com.strum.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_project_dashboard.*

class ProjectDashboard : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        tabLayout = findViewById(R.id.tasksTabs)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("My Tasks"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("All Tasks"))


        teammembrv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        backprojDash.setOnClickListener{
            finish()
        }

        projectFAB.setOnClickListener{
            var intent = Intent(applicationContext, CreateTask::class.java)
            startActivity(intent)
        }
        //get team memb list form db


        // add only 3 elements
        var teamList = ArrayList<TeamMemberModel>()

        teamList.add(TeamMemberModel("https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg", "Mia"))
        teamList.add(TeamMemberModel("https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg-1024x683.jpg", "Mia"))

        var teamListShort = ArrayList<TeamMemberModel>()

        if(teamList.size<=3){
            adapter = TeamAdapter(applicationContext, teamList, teamList.size)
            teammembrv.adapter = adapter
        }

        else{
            while (teamListShort.size < 3){
                var randomPerson = teamList.random()
                teamListShort.add(randomPerson)
            }
            teamListShort.add(TeamMemberModel("END", "DONE"))
            adapter = TeamAdapter(applicationContext, teamListShort, teamList.size)
            teammembrv.adapter = adapter
        }
    }
}
