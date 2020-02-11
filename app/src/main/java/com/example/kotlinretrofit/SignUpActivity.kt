package com.example.kotlinretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_btn.setOnClickListener {
            Client.retrofitService.SignUp(
                email_txt.text.toString(),
                password_txt.text.toString(),
                name_txt.text.toString(),
                age_txt.id.toInt()

            ).enqueue(object : retrofit2.Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    when (response!!.code()) {
                        200 -> {
                            Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        405 -> Toast.makeText(
                            this@SignUpActivity,
                            "회원가입 실패 : 아이디나 비번이 올바르지 않습니다",
                            Toast.LENGTH_LONG
                        ).show()
                        500 -> Toast.makeText(
                            this@SignUpActivity,
                            "회원가입 실패 : 서버 오류",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "통신 실패", Toast.LENGTH_LONG).show()
                    Log.d("Fail Connection", "msg: " + t.message)
                }
            })
        }
    }
}
