package com.strum.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.strum.app.network.BasicAuthInterceptor
import kotlinx.android.synthetic.main.activity_main.*

import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainPageAdapter.ItemClickListener {

    var models = ArrayList<MainScreenModel>()
    var fname = ""
    var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Get request to get noOfPersonalTasks, WorkTasks, CompletedTasks etc

        val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(getUserName(), getPassword())).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://strum-task-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build()

        val api = retrofit.create(BackendApi::class.java)

        api.getUserInfo().enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                Log.d("Response", response.body().toString())
                fname = response.body()!!.username
                userId = response.body()!!.userid
                greetingsFname.text = "Hello, $fname"
            }

        })


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
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    fun getUserName(): String{
        return "anshul"
    }
    fun getPassword(): String{
        return "1234"
    }
}
