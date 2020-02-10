package com.strum.app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.strum.app.R
import com.strum.app.adapters.TeamAdapter
import com.strum.app.adapters.WorkTaskAdapter
import com.strum.app.models.ProjectDetailModel
import com.strum.app.models.User
import com.strum.app.models.WorkTask
import com.strum.app.network.BasicAuthInterceptor
import com.strum.app.services.BackendApi
import kotlinx.android.synthetic.main.activity_project_dashboard.*
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectDashboard : AppCompatActivity(), WorkTaskAdapter.StatusChangeListener {

    var tabLayout: TabLayout? = null
    lateinit var adapter: TeamAdapter

    lateinit var taskadapter: WorkTaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        tabLayout = findViewById(R.id.tasksTabs)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("My Tasks"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("All Tasks"))

        val sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        val currentUserId = sharedPreferences.getInt("userId", -1)
        val username = sharedPreferences.getString("userName", "")
        val pwd = sharedPreferences.getString("password", "")

        teammembrv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mytasksRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        ProjectName.text = intent.getStringExtra("projectName")
        ProjectAdminDetails.text = intent.getStringExtra("projectAdmin")

        var projectId = intent.getIntExtra("projId", -1)

        var teamList = ArrayList<User>()
        var teamListShort = ArrayList<User>()

        var myTasksList = ArrayList<WorkTask>()
        var allTaskList = ArrayList<WorkTask>()

        taskadapter = WorkTaskAdapter(applicationContext, myTasksList, this, currentUserId)

        mytasksRv.adapter = taskadapter

        //get project details from id

        if(projectId!=-1&&username!=""&&pwd!=""){
            val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    BasicAuthInterceptor(username!!,
                        pwd!!)
                ).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://strum-task-manager.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultHttpClient)
                .build()

            val api = retrofit.create(BackendApi::class.java)

            api.getProjectDetails(projectId).enqueue(object : Callback<ProjectDetailModel>{
                override fun onFailure(call: Call<ProjectDetailModel>, t: Throwable) {
                    Log.d("onfail", "fail")
                }

                override fun onResponse(
                    call: Call<ProjectDetailModel>,
                    response: retrofit2.Response<ProjectDetailModel>
                ) {
                    Log.d("projectdetails", response.body()!!.description)
                    projectDesc.text = response.body()!!.description
                    // make team list



                    for(teammember in response.body()!!.teamList)
                    {
                        teamList.add(User(teammember.userid, teammember.username, teammember.url))
                    }

                    if(teamList.size<=3){
                        teamListShort = teamList
                        adapter = TeamAdapter(applicationContext, teamListShort, teamList.size)

                        teammembrv.adapter = adapter
                    }

                    else{
                        while (teamListShort.size < 3){
                            var randomPerson = teamList.random()
                            teamListShort.add(randomPerson)
                        }
                        teamListShort.add(
                            User(
                                -1,
                                "END",
                                "DONE"
                            )
                        )
                        adapter = TeamAdapter(applicationContext, teamListShort, teamList.size)

                        teammembrv.adapter = adapter
                    }
                    adapter.notifyDataSetChanged()

                    //get tasks
                    Log.d("tasksdash", response.body()!!.taskList.toString())

                    for(task in response.body()!!.taskList){
                        if(task.userid==currentUserId){
                            myTasksList.add(WorkTask(task.taskname,
                                task.deadline, task.taskid,
                                task.priority, task.userid, task.status))
                        }
                        else{
                            allTaskList.add(WorkTask(task.taskname,
                                task.deadline, task.taskid,
                                task.priority, task.userid, task.status))
                        }
                    }

                    allTaskList.addAll(myTasksList)

                    Log.d("tasksnew", myTasksList.toString())

                    taskadapter.notifyDataSetChanged()

                    mytasksRv.adapter = taskadapter
                }

            })
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if(p0?.position==0){
                    taskadapter = WorkTaskAdapter(applicationContext, myTasksList, this@ProjectDashboard, currentUserId)
                    mytasksRv.adapter = taskadapter
                }
                else if(p0?.position==1){
                    taskadapter = WorkTaskAdapter(applicationContext, allTaskList, this@ProjectDashboard, currentUserId)
                    mytasksRv.adapter = taskadapter
                }
            }

        })

        backprojDash.setOnClickListener{
            finish()
        }

        projectFAB.setOnClickListener{
            var intent = Intent(applicationContext, CreateTask::class.java)
            intent.putExtra("projId", projectId)
            startActivity(intent)
        }
        //get team memb list form db


        // add only 3 elements

//        teamList.add(
//            TeamMemberModel(
//                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
//                1,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
//                2,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
//                3,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
//                4,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
//                5,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
//                6,
//                "Mia"
//            )
//        )
//        teamList.add(
//            TeamMemberModel(
//                "https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg-1024x683.jpg",
//                7,
//                "Mia"
//            )
//        )


    }

    override fun onStatusChanged(
        status: String,
        taskid: Int,
        priority: String,
        taskName: String,
        date: String,
        userid: Int
    ) {

    }
}
