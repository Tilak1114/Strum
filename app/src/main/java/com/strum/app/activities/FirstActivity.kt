package com.strum.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strum.app.R
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        loginBtn.setOnClickListener{
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        signupBtn.setOnClickListener{
            var intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
