package com.strum.app.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.strum.app.R
import com.strum.app.models.User
import com.strum.app.services.BackendApi
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

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Creating Account...")

        signupBack.setOnClickListener{
            finish()
        }

        signupSubmit.setOnClickListener{
            if(usernameInput.text.isEmpty()){
                usernameInput.error = "Required"
            }
            if(passInputsnp.text.isEmpty()){
                passInputsnp.error = "Password beko laude"
            }
            if(cpwdet.text.isEmpty()){
                cpwdet.error = "Confirmation required"
            }
            if(cpwdet.text.toString()!=passInputsnp.text.toString()){
                Toast.makeText(applicationContext, "Passwords don't match", Toast.LENGTH_LONG).show()
            }
            if(profileurlet.text.isEmpty()){
                profileurlet.error = "We want to see your beautiful face"
            }
            if(!usernameInput.text.isEmpty()&&!passInputsnp.text.isEmpty()&&!cpwdet.text.isEmpty()
                && passInputsnp.text.toString() == cpwdet.text.toString()
                && !profileurlet.text.toString().isEmpty()
            ){
                progressDialog.show()
                val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val request = chain.request()
                            val authenticatedRequest = request.newBuilder()
                                .header("Content-Type", "application/json").build()
                            return chain.proceed(authenticatedRequest)
                        }
                    }).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://strum-task-manager.herokuapp.com")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(defaultHttpClient)
                    .build()

                val api = retrofit.create(BackendApi::class.java)
                var jsonObject : JSONObject = JSONObject()
                jsonObject.put("username", usernameInput.text.toString())
                jsonObject.put("password", passInputsnp.text.toString())
                jsonObject.put("profileurl", profileurlet.text.toString())

                api.newUser(jsonObject.toString())?.enqueue(object : Callback<User?> {
                    override fun onFailure(call: Call<User?>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<User?>, response: retrofit2.Response<User?>) {
                        if(response.isSuccessful){
                            progressDialog.setMessage("Success")
                            progressDialog.dismiss()
                            Log.d("Response", response.body()?.username.toString())
                            var intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext, "Signup error", Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }
        }
    }
}
