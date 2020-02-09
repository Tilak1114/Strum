package com.strum.app.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.strum.app.R
import com.strum.app.models.User
import com.strum.app.network.BasicAuthInterceptor
import com.strum.app.services.BackendApi
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Authenticating...")
        progressDialog.setCancelable(false)

        backLogin.setOnClickListener{
            finish()
        }

        loginSubmit.setOnClickListener{
            if(usernameInput.text.isEmpty()){
                usernameInput.error = "Username Required"
            }
            if(passInput.text.isEmpty()){
                passInput.error = "Password Required"
            }
            if(!usernameInput.text.isEmpty()&&!passInput.text.isEmpty()){
                progressDialog.show()
                var pass = passInput.text.toString()
                Toast.makeText(applicationContext, passInput.text.toString(), Toast.LENGTH_LONG).show()
                val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(usernameInput.text.toString(),
                        passInput.text.toString())).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://strum-task-manager.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(defaultHttpClient)
                    .build()

                val api = retrofit.create(BackendApi::class.java)

                api.getUserInfo().enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        progressDialog.dismiss()
                    }

                    override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                        progressDialog.dismiss()
                        Log.d("Response", response.body().toString())
                        if(response.isSuccessful){
                            var username = response.body()!!.username
                            var userid = response.body()!!.userid
                            var userUrl = response.body()!!.url


                            //save data into shared preferences

                            var sharedPreferences = getSharedPreferences("MySharedPref",
                                MODE_PRIVATE)
                            var myEdit = sharedPreferences.edit()
                            myEdit.putString("userName", username)
                            myEdit.putString("password", passInput.text.toString())
                            myEdit.putInt("userId", userid)
                            myEdit.putString("userUrl", userUrl)
                            myEdit.putBoolean("hasLoggedIn", true)

                            myEdit.apply()

//                            fname = response.body()!!.username
//                            userId = response.body()!!.userid
//                            greetingsFname.text = "Hello, $fname"
                            Toast.makeText(applicationContext, "Logged in as ${userid}"
                            , Toast.LENGTH_LONG).show()
                            var intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("userName", username)
                            intent.putExtra("userId", userid)
                            intent.putExtra("userUrl", userUrl)
                            startActivity(intent)
                        }
                        else
                            Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }
}
