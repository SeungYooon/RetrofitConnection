package com.example.kotlinretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kotlinretrofit.dto.Back
import com.example.kotlinretrofit.service.ApiClient
import com.example.kotlinretrofit.service.RetrofitInterface
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val retrofitInterface: RetrofitInterface =
            ApiClient().getApiClient()!!.create(RetrofitInterface::class.java)

        sign_btn.setOnClickListener {
            retrofitInterface.test2().enqueue(object : Callback<ArrayList<Back>> {
                override fun onResponse(
                    call: Call<ArrayList<Back>>,
                    response: Response<ArrayList<Back>>
                ) {
                    if (response.isSuccessful()) {
                        Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                        finish()
                    }

                    when (response.code()) {

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


                override fun onFailure(call: Call<ArrayList<Back>>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "통신 실패", Toast.LENGTH_LONG).show()
                    Log.d("Fail Connection", "msg: " + t.message)
                }
            })
        }
    }
}
