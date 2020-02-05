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

        projectsRv.layoutManager = GridLayoutManager(this, 4)

        var list = ArrayList<ProjectModel>()
        list.add(ProjectModel(12, "CP", "Cyber Punk", "Anshul"))
        list.add(ProjectModel(13, "FB", "Fill BinLaden", "Kunal"))
        list.add(ProjectModel(14, "PR", "Practo Ray", "Anshul"))
        list.add(ProjectModel(16, "SG", "Swiggy Gold", "Kunal"))
        list.add(ProjectModel(15, "IN", "Invictus", "Anshul"))
        list.add(ProjectModel(17, "LB", "Lambda bros", "Kunal"))
        list.add(ProjectModel(18,"SV", "Smirnoff Vodka", "Anshul"))

        projecttv.text = "Projects ( ${list.size} )"

        var adapter = ProjectAdapter(applicationContext, list)
        projectsRv.adapter = adapter
    }
}
