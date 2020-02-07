package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_create_task.*

class CreateTask : AppCompatActivity() {

    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        tabLayout = findViewById(R.id.prioSelTab)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("LOW"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("MED"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("HIGH"))

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
}
