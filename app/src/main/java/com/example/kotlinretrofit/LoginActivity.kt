package com.example.kotlinretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinretrofit.di.SharedPref
import com.example.kotlinretrofit.dto.Back
import com.example.kotlinretrofit.service.ApiClient
import com.example.kotlinretrofit.service.RetrofitInterface
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val PREFERENCE = "com.example.kotlinretrofit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SharedPref.openSharedPrep(this)

        val retrofitInterface: RetrofitInterface =
            ApiClient().getApiClient()!!.create(RetrofitInterface::class.java)

        login_btn.setOnClickListener {
            retrofitInterface.test().enqueue(object : Callback<ArrayList<Back>> {
                override fun onResponse(
                    call: Call<ArrayList<Back>>,
                    response: Response<ArrayList<Back>>
                ) {
                    if (response.isSuccessful()) {
                        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("email", email_txt.text.toString())
                        editor.apply()
                        finish()
                        startActivity(Intent(this@LoginActivity, RecyclerActivity::class.java))
                    }
                    when (response.code()) {

                        405 -> Toast.makeText(
                            this@LoginActivity,
                            "로그인 실패 : 아이디나 비번이 올바르지 않습니다",
                            Toast.LENGTH_LONG
                        ).show()
                        500 -> Toast.makeText(
                            this@LoginActivity,
                            "로그인 실패 : 서버 오류",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Back>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "통신 실패", Toast.LENGTH_LONG).show()
                    Log.d("Fail Connection", "error: " + t.message)
                }

            })
        }
        signup_go.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        }
    }
}