package com.strum.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pageritem.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainPageAdapter.ItemClickListener {

    var models = ArrayList<MainScreenModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Get request to get noOfPersonalTasks, WorkTasks, CompletedTasks etc

        var fname = "Kassandra"

        var noOfPersonalTasks = 4
        var totalPersonalTasks = 15
        var noOfWorkTasks = 2
        var totalWorkTasks = 3

        var totalCompletedTasks = 45

        var date = Calendar.getInstance().time
        var formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(date)

        todayDate.text = "Today: $formattedDate"
        mainCompletedTasksTv.text = "$totalCompletedTasks tasks completed"
        greetingsFname.text = "Hello, $fname"

        var perProg = (noOfPersonalTasks.toFloat()/totalPersonalTasks)*100
        var workProg = (noOfWorkTasks.toFloat()/totalWorkTasks)*100

        models.add(MainScreenModel("Personal", noOfPersonalTasks, totalPersonalTasks, R.drawable.icn_personal, perProg.toInt()))
        models.add(MainScreenModel("Work", noOfWorkTasks, totalWorkTasks, R.drawable.icn_work, workProg.toInt()))

        var adapter =  MainPageAdapter(models, applicationContext, this)

        mainViewPager.adapter = adapter
        mainViewPager.setPadding(20, 0, 100, 0)

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

    override fun onItemClick(itemId: Int?, progress: Int?) {
        if(itemId==0){
            val intent = Intent(applicationContext, PersonalActivity::class.java)
            intent.putExtra("progress", progress)
//            val p1 = Pair(pagerCard as View, "laytrans")
//            val p2 = Pair(mainPagerItemIcn as View, "cardIcn")
//            val p3 = Pair(mainPagerItemTitle as View, "cardTitle")
//            val p4 = Pair(mainPagerItemSubtitle as View, "cardSub")
//            val p5 = Pair(mainPagerItemPgBar as View, "cardPb")
//            val options =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
            startActivity(intent)
        }
        else if(itemId==1){
            val intent = Intent(applicationContext, WorkActivity::class.java)
            intent.putExtra("progress", progress)
            startActivity(intent)
        }
    }
}
