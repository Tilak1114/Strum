package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var models = ArrayList<MainScreenModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Get request to get noOfPersonalTasks, WorkTasks, CompletedTasks

        var noOfPersonalTasks = "15 Tasks"
        var noOfWorkTasks = "3 Tasks"
        var completedTasks = "45 Tasks Completed"

        mainCompletedTasksTv.text = completedTasks

        models.add(MainScreenModel("Personal", noOfPersonalTasks, R.drawable.icn_personal, 60))
        models.add(MainScreenModel("Work", noOfWorkTasks, R.drawable.icn_personal, 90))

        var adapter =  MainPageAdapter(models, applicationContext)

        mainViewPager.adapter = adapter
        mainViewPager.setPadding(100, 0, 100, 0)
    }
}
