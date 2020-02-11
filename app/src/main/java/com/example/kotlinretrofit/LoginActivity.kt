package com.example.kotlinretrofit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    val PREFERENCE = "com.example.kotlinretrofit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SharedPref.openSharedPrep(this)
        login_btn.setOnClickListener {
            Client.retrofitService.logIn(email_txt.text.toString(), password_txt.text.toString())
                .enqueue(object : retrofit2.Callback<Auth> {
                    override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                        when (response!!.code()) {
                            200 -> {
                                val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                                val editor = pref.edit()
                                editor.putString("username", email_txt.text.toString())
                                editor.commit()
                                finish()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            }
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

                    override fun onFailure(call: Call<Auth>, t: Throwable) {

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