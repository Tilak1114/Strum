package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_work.*

class WorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        val prog = intent.getIntExtra("progress", 0)

        workPgBar.setProgress(prog)
        wprogressTv.text = prog.toString()+"%"

        backwork.setOnClickListener{
            finish()
        }

        projectsRv.layoutManager = GridLayoutManager(this, 3)

        var list = ArrayList<ProjectModel>()
        list.add(ProjectModel("CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel("FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel("CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel("FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel("CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel("FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel("CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel("FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel("CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel("FB", "Fill BinLaden", "Kunal"))

        var adapter = ProjectAdapter(applicationContext, list)
        projectsRv.adapter = adapter
    }
}
