package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_create_task.*

class CreateTask : AppCompatActivity(), AssigneeAdapter.AssigneeSelection {

    var tabLayout: TabLayout? = null
    lateinit var adapter: AssigneeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)


        var assigneeList = ArrayList<TeamMemberModel>()

        assigneeList.add(TeamMemberModel("https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg", 2,"Mia"))
        assigneeList.add(TeamMemberModel("https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg", 1,"Mia"))

        adapter = AssigneeAdapter(applicationContext, assigneeList, this)

        assignSelRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        assignSelRv.adapter = adapter

        tabLayout = findViewById(R.id.prioSelTab)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("LOW"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("MED"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("HIGH"))

        backCreateTask.setOnClickListener{
            finish()
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("WhichTab", tab.text.toString())
            }
        })


    }

    override fun onSelected(userId: Int) {
        Log.d("assignsel", userId.toString())
    }
}
