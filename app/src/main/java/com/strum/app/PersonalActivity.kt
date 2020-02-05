package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_personal.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class PersonalActivity : AppCompatActivity(), PersonalTaskAdapter.StatusChangeListener {

    var list = ArrayList<PersonalTaskModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        val prog = intent.getIntExtra("progress", 0)

        progressTv.text = prog.toString()+"%"
        personalPgBar.progress = prog

        backpersonal.setOnClickListener{
            finish()
        }

        tasksrv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var date = Calendar.getInstance().time
//        var formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(date)
        list.add(PersonalTaskModel(1,"HIGH", "Get Milk", date, "Pending"))
        list.add(PersonalTaskModel(2, "HIGH", "Go to the Gym", date, "Completed"))
        list.add(PersonalTaskModel(3, "MED", "Feed the baby", date, "Pending"))
        list.add(PersonalTaskModel(4, "HIGH", "Get stuff", date, "Completed"))
        list.add(PersonalTaskModel(5,"LOW", "Go Swimming", date, "Pending"))
        list.add(PersonalTaskModel(6,"HIGH", "Meet Steve", date, "Completed"))

        list = refactorList(list)

        var taskNumber = getPendingTasks(list)

        taskstv.text = "Tasks ( $taskNumber )"

        var adapter = PersonalTaskAdapter(applicationContext, list, this)

        tasksrv.adapter = adapter
    }

    private fun getPendingTasks(list: ArrayList<PersonalTaskModel>): Int {
        var taskCount = 0
        list.forEach {l ->
            if(l.status == "Pending")
                taskCount++
        }
        return taskCount
    }

    private fun refactorList(list: ArrayList<PersonalTaskModel>): ArrayList<PersonalTaskModel> {
        var compList = ArrayList<PersonalTaskModel>()
        var pendList = ArrayList<PersonalTaskModel>()
        list.forEach { listitem ->
            if(listitem.status == "Completed")
                compList.add(listitem)
            else
                pendList.add(listitem)
        }
        pendList.addAll(compList)
        return pendList
    }

    override fun onStatusChanged(
        status: String,
        id: Int,
        priority: String,
        taskName: String,
        date: Date
    ) {
        if(status.equals("Completed")){
            var temp = PersonalTaskModel(id, priority, taskName, date, status)
            list.remove(temp)
            list.add(temp)

            var adapter = PersonalTaskAdapter(applicationContext, list, this)

            tasksrv.adapter = adapter

            taskstv.text = "Tasks ( ${getPendingTasks(list)} )"
        }
    }

}
