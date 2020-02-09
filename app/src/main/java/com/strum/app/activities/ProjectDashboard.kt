package com.strum.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.strum.app.R
import com.strum.app.adapters.TeamAdapter
import com.strum.app.models.ProjectDetailModel
import com.strum.app.models.TeamMemberModel
import com.strum.app.network.BasicAuthInterceptor
import com.strum.app.services.BackendApi
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_project_dashboard.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.usernameInput
import kotlinx.android.synthetic.main.project_lay.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

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

        ProjectName.text = intent.getStringExtra("projectName")
        ProjectAdminDetails.text = intent.getStringExtra("projectAdmin")

        var projectId = intent.getIntExtra("projectId", -1)

        //get project details from id

        if(projectId!=-1){
            val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    BasicAuthInterceptor("nitheesh",
                        "12345")
                )
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        val authenticatedRequest = request.newBuilder()
                            .header("Content-Type", "application/json")
                            .build()
                        return chain.proceed(authenticatedRequest)
                    }
                }).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://strum-task-manager.herokuapp.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultHttpClient)
                .build()

            val api = retrofit.create(BackendApi::class.java)

            api.getProjectDetails(projectId).enqueue(object : Callback<ProjectDetailModel>{
                override fun onFailure(call: Call<ProjectDetailModel>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ProjectDetailModel>,
                    response: retrofit2.Response<ProjectDetailModel>
                ) {
                    Log.d("projectdetails", response.body()!!.description)
                }

            })
        }

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

        teamList.add(
            TeamMemberModel(
                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
                1,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
                2,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
                3,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
                4,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
                5,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://i.pinimg.com/originals/f6/ff/6f/f6ff6fe05f20905f24f2f4e72ae8891d.jpg",
                6,
                "Mia"
            )
        )
        teamList.add(
            TeamMemberModel(
                "https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg-1024x683.jpg",
                7,
                "Mia"
            )
        )

        var teamListShort = ArrayList<TeamMemberModel>()

        if(teamList.size<=3){
            adapter = TeamAdapter(
                applicationContext,
                teamList,
                teamList.size
            )
            teammembrv.adapter = adapter
        }

        else{
            while (teamListShort.size < 3){
                var randomPerson = teamList.random()
                teamListShort.add(randomPerson)
            }
            teamListShort.add(
                TeamMemberModel(
                    "END",
                    -1,
                    "DONE"
                )
            )
            adapter = TeamAdapter(
                applicationContext,
                teamListShort,
                teamList.size
            )
            teammembrv.adapter = adapter
        }
    }
}
