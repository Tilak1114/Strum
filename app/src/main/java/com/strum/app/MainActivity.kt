package com.strum.app

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var models = ArrayList<MainScreenModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Get request to get noOfPersonalTasks, WorkTasks, CompletedTasks etc

        var fname = "Kassandra"

        var noOfPersonalTasks = "15 Tasks"
        var noOfWorkTasks = "3 Tasks"
        var completedTasks = "45 Tasks Completed"

        var date = Calendar.getInstance().time
        var formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(date)

        todayDate.text = "Today: $formattedDate"
        mainCompletedTasksTv.text = completedTasks
        greetingsFname.text = "Hello, $fname"

        models.add(MainScreenModel("Personal", noOfPersonalTasks, R.drawable.icn_personal, 20))
        models.add(MainScreenModel("Work", noOfWorkTasks, R.drawable.icn_work, 90))

        var adapter =  MainPageAdapter(models, applicationContext)

        mainViewPager.adapter = adapter
        mainViewPager.setPadding(0, 0, 100, 0)

        mainViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(position==0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        var window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.personalColor)
                    }
                    mainactivityLay.setBackgroundResource(R.drawable.back_grad)
                }
                else{
                    mainactivityLay.setBackgroundResource(R.drawable.back_grad_sec)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        var window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.workColorbar)
                    }
                }
            }
            override fun onPageSelected(position: Int) {

            }
        })
    }
}
