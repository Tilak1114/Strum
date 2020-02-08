package com.strum.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.strum.app.R
import com.strum.app.adapters.ProjectAdapter
import com.strum.app.models.ProjectModel
import com.strum.app.models.User
import com.strum.app.services.BackendApi
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

        workPgBar.setProgress(prog)
        wprogressTv.text = prog.toString()+"%"

        //temp signup code

//        val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(object : Interceptor {
//                @Throws(IOException::class)
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val request = chain.request()
//                    val authenticatedRequest = request.newBuilder()
//                        .header("Content-Type", "application/json").build()
//                    return chain.proceed(authenticatedRequest)
//                }
//            }).build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://strum-task-manager.herokuapp.com")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(defaultHttpClient)
//            .build()
//
//        val api = retrofit.create(BackendApi::class.java)
//        var jsonObject : JSONObject = JSONObject()
//        jsonObject.put("username", "khan")
//        jsonObject.put("password", "boom")
//
//        api.newUser(jsonObject.toString())?.enqueue(object : Callback<User?>{
//            override fun onFailure(call: Call<User?>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<User?>, response: retrofit2.Response<User?>) {
//                Log.d("Response", response.body()?.username.toString())
//            }
//
//        })

        backwork.setOnClickListener{
            finish()
        }

        projectsRv.layoutManager = GridLayoutManager(this, 4)

        //get all projects


        var list = ArrayList<ProjectModel>()
        list.add(
            ProjectModel(
                12,
                "CP",
                "Cyber Punk",
                "Anshul"
            )
        )
        list.add(
            ProjectModel(
                13,
                "FB",
                "Fill BinLaden",
                "Kunal"
            )
        )
        list.add(
            ProjectModel(
                14,
                "PR",
                "Practo Ray",
                "Anshul"
            )
        )
        list.add(
            ProjectModel(
                16,
                "SG",
                "Swiggy Gold",
                "Kunal"
            )
        )
        list.add(
            ProjectModel(
                15,
                "IN",
                "Invictus",
                "Anshul"
            )
        )
        list.add(
            ProjectModel(
                17,
                "LB",
                "Lambda bros",
                "Kunal"
            )
        )
        list.add(
            ProjectModel(
                18,
                "SV",
                "Smirnoff Vodka",
                "Anshul"
            )
        )

        projecttv.text = "Projects ( ${list.size} )"

        var adapter =
            ProjectAdapter(applicationContext, list, this)
        projectsRv.adapter = adapter
    }

    private fun getUserName(): String {
        return "anshul"
    }

    private fun getPassword(): String{
        return ""
    }

    override fun onProjectClick(projectId: Int) {
        val intent = Intent(applicationContext, ProjectDashboard::class.java)
        startActivity(intent)
    }
}
