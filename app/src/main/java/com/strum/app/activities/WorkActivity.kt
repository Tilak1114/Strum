package com.strum.app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.strum.app.R
import com.strum.app.adapters.ProjectAdapter
import com.strum.app.models.ProjectModel
import com.strum.app.models.ProjectResponse
import com.strum.app.models.User
import com.strum.app.network.BasicAuthInterceptor
import com.strum.app.services.BackendApi
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_work.*
import okhttp3.Credentials
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


class WorkActivity : AppCompatActivity(), ProjectAdapter.ProjectClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        val prog = intent.getIntExtra("progress", 0)
        val userId = intent.getIntExtra("userId", -1)

        val usr = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString("userName", "")
        val pwd = getSharedPreferences("MySharedPPref", Context.MODE_PRIVATE).getString("password", "")

        Log.d("shrdword", usr+pwd)
        workPgBar.setProgress(prog)
        wprogressTv.text = prog.toString()+"%"

        projectsRv.layoutManager = GridLayoutManager(this, 4)

        var list = ArrayList<ProjectModel>()

        var adapter =
            ProjectAdapter(applicationContext, list, this)
        projectsRv.adapter = adapter

        //fetch all projects

        val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                BasicAuthInterceptor(usr!!,
                    "12345")
            ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://strum-task-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build()

        val api = retrofit.create(BackendApi::class.java)

        api.getProjects().enqueue(object : Callback<ProjectResponse>{
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Log.d("Projectres", t.message.toString())
            }

            override fun onResponse(
                call: Call<ProjectResponse>,
                response: retrofit2.Response<ProjectResponse>
            ) {
                Log.d("Projectres", response.code().toString())
                if(response.isSuccessful){
                    Log.d("printRes", response.body().toString())
                    for(project in response.body()!!.projects){
                        Log.d("shakira", project.projFName)
                        list.add(ProjectModel(
                            project.projId,
                            genereateProjectSName(project.projFName),
                            project.projFName,
                            project.projectAdmin))
                    }
                    adapter.notifyDataSetChanged()

                    Log.d("shakirasize", list.size.toString())
                    projecttv.text = "Projects ( ${list.size} )"
                }
            }

        })

        backwork.setOnClickListener{
            finish()
        }

        //get all projects

//        list.add(
//            ProjectModel(
//                12,
//                "CP",
//                "Cyber Punk",
//                "Anshul"
//            )
//        )
//        list.add(
//            ProjectModel(
//                13,
//                "FB",
//                "Fill BinLaden",
//                "Kunal"
//            )
//        )
//        list.add(
//            ProjectModel(
//                14,
//                "PR",
//                "Practo Ray",
//                "Anshul"
//            )
//        )
//        list.add(
//            ProjectModel(
//                16,
//                "SG",
//                "Swiggy Gold",
//                "Kunal"
//            )
//        )
//        list.add(
//            ProjectModel(
//                15,
//                "IN",
//                "Invictus",
//                "Anshul"
//            )
//        )
//        list.add(
//            ProjectModel(
//                17,
//                "LB",
//                "Lambda bros",
//                "Kunal"
//            )
//        )
//        list.add(
//            ProjectModel(
//                18,
//                "SV",
//                "Smirnoff Vodka",
//                "Anshul"
//            )
//        )
    }

    private fun getUserName(): String {
        return "anshul"
    }

    private fun getPassword(): String{
        return ""
    }

    fun genereateProjectSName(projectname: String): String{
        var strArray = projectname.split(" ")
        var result: String
        result = if(strArray.size>1){
            strArray.toString().toCharArray()[0+1].toString()+strArray.toString().toCharArray()[1+1]
        } else{
            var singleWordArray = strArray.toString().toCharArray()
            singleWordArray[0+1].toString()+singleWordArray[1+1]
        }
        return result.toUpperCase()
    }

    override fun onProjectClick(projectId: Int, projectName: String, projectAdmin: String) {
        //Toast.makeText(applicationContext, projectId.toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(applicationContext, ProjectDashboard::class.java)
        intent.putExtra("projId", projectId)
        intent.putExtra("projectName", projectName)
        intent.putExtra("projectAdmin", projectAdmin)
        startActivity(intent)
    }
}
