package com.strum.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_work.*

class WorkActivity : AppCompatActivity(), ProjectAdapter.ProjectClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        val prog = intent.getIntExtra("progress", 0)
        val userId = intent.getIntExtra("userId", -1)

        workPgBar.setProgress(prog)
        wprogressTv.text = prog.toString()+"%"

        backwork.setOnClickListener{
            finish()
        }

        projectsRv.layoutManager = GridLayoutManager(this, 4)

        //get all projects



        var list = ArrayList<ProjectModel>()
        list.add(ProjectModel(12, "CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel(13, "FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel(14, "PR", "Practo Ray", "Anshul"))
        list.add(ProjectModel(16, "SG", "Swiggy Gold", "Kunal"))
        list.add(ProjectModel(15, "IN", "Invictus", "Anshul"))
        list.add(ProjectModel(17, "LB", "Lambda bros", "Kunal"))
        list.add(ProjectModel(18,"SV", "Smirnoff Vodka", "Anshul"))

        projecttv.text = "Projects ( ${list.size} )"

        var adapter = ProjectAdapter(applicationContext, list, this)
        projectsRv.adapter = adapter
    }

    override fun onProjectClick(projectId: Int) {
        val intent = Intent(applicationContext, ProjectDashboard::class.java)
        startActivity(intent)
    }
}
