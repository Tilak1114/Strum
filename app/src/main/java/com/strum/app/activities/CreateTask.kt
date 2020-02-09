package com.strum.app.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.strum.app.R
import com.strum.app.adapters.AssigneeAdapter
import com.strum.app.adapters.TeamAdapter
import com.strum.app.models.ProjectDetailModel
import com.strum.app.models.TeamMemberModel
import com.strum.app.models.User
import com.strum.app.models.WorkTask
import com.strum.app.network.BasicAuthInterceptor
import com.strum.app.services.BackendApi
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_project_dashboard.*
import kotlinx.android.synthetic.main.activity_signup.*
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

class CreateTask : AppCompatActivity(), AssigneeAdapter.AssigneeSelection {

    var tabLayout: TabLayout? = null
    lateinit var adapter: AssigneeAdapter
    var prioritySel: String = ""
    var assigneeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        var assigneeList = ArrayList<User>()

        var progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Adding Task...")

        adapter = AssigneeAdapter(
            applicationContext,
            assigneeList,
            this
        )

        assignSelRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        assignSelRv.adapter = adapter

        var projectId = intent.getIntExtra("projId", -1)

        //fetch team from project id

        if(projectId!=-1){
            val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    BasicAuthInterceptor("nitheesh",
                        "12345")
                ).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://strum-task-manager.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultHttpClient)
                .build()

            val api = retrofit.create(BackendApi::class.java)

            api.getProjectDetails(projectId).enqueue(object : Callback<ProjectDetailModel> {
                override fun onFailure(call: Call<ProjectDetailModel>, t: Throwable) {
                    Log.d("onfail", "fail")
                }

                override fun onResponse(
                    call: Call<ProjectDetailModel>,
                    response: retrofit2.Response<ProjectDetailModel>
                ) {
                    if(response.isSuccessful){
                        Log.d("projectdetails", response.body()!!.description)
                        for(assignee in response.body()!!.teamList){
                            assigneeList.add(User(assignee.userid, assignee.username, "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg"))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

            })
        }

//        assigneeList.add(
//            TeamMemberModel(
//                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
//                2,
//                "Mia"
//            )
//        )
//        assigneeList.add(
//            TeamMemberModel(
//                "https://s.abcnews.com/images/Politics/vladimir-putin-file-rt-jef-200124_hpMain_16x9_992.jpg",
//                1,
//                "Mia"
//            )
//        )



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
                prioritySel = tab.text.toString()
            }
        })

        createDone.setOnClickListener{
            if(taskNameInput.text.toString().isEmpty()){
                taskNameInput.error = "Required"
            }
            if(deadlineEt.text.toString().isEmpty()){
                deadlineEt.error = "Required"
            }
            if(prioritySel.isEmpty()){
                Toast.makeText(applicationContext, "Select Priority", Toast.LENGTH_LONG).show()
            }
            if(assigneeId==-1){
                Toast.makeText(applicationContext, "Assign your task", Toast.LENGTH_LONG).show()
            }
            if(!taskNameInput.text.toString().isEmpty()&&!deadlineEt.text.toString().isEmpty()
                &&!prioritySel.isEmpty()&&assigneeId!=-1){
                progressDialog.show()
                val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor("nitheesh", "12345")).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://strum-task-manager.herokuapp.com")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(defaultHttpClient)
                    .build()

                val api = retrofit.create(BackendApi::class.java)
                var jsonObject : JSONObject = JSONObject()
                jsonObject.put("userid", assigneeId)
                jsonObject.put("taskname", taskNameInput.text.toString())
                jsonObject.put("projectid", projectId)
                jsonObject.put("deadline", deadlineEt.text.toString())
                jsonObject.put("priority", prioritySel)

                api.newTask(jsonObject.toString())?.enqueue(object : Callback<WorkTask?> {
                    override fun onFailure(call: Call<WorkTask?>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<WorkTask?>, response: retrofit2.Response<WorkTask?>) {
                        if(response.isSuccessful){
                            progressDialog.setMessage("Success")
                            progressDialog.dismiss()
                            Log.d("Response", response.body()?.taskname.toString())
                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()
                        }
                    }

                })
            }
        }


    }

    override fun onSelected(userId: Int) {
        Log.d("assignsel", userId.toString())
        assigneeId = userId
    }
}
