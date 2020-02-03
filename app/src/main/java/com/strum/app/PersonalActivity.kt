package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_personal.*

class PersonalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        val prog = intent.getIntExtra("progress", 0)

        progressTv.text = prog.toString()+"%"
        personalPgBar.progress = prog

        backpersonal.setOnClickListener{
            finish()
        }


    }
}
