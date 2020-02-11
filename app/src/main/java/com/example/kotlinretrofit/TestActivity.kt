package com.example.kotlinretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestActivity : AppCompatActivity() {

    var login: Auth? = null
    var spec: Spec? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.123.103:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService: LoginService = retrofit.create(LoginService::class.java)

        btn_login.setOnClickListener {
            var text1 = edit_email.text.toString()
            var text2 = edit_password.text.toString()

            loginService.requestLogin(text1, text2).enqueue(object : Callback<Auth> {
                override fun onFailure(call: Call<Auth>, t: Throwable) {
                    Log.e("LOGIN", t.message)
                    var dialog =
                        AlertDialog.Builder(this@TestActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    login = response.body()
                    Log.d("LOGIN", "email: " + login?.email)
                    Log.d("LOGIN", "password:" + response.body()?.password)

                    var dialog = AlertDialog.Builder(this@TestActivity)
                    dialog.setTitle("알림")
                    dialog.setMessage("email:" + text1)
                    dialog.show()
                }
            })
        }

        btn_get.setOnClickListener {

            loginService.getContents(get_txt.toString(), get_txt2.toString())
                .enqueue(object : Callback<Spec> {
                    override fun onFailure(call: Call<Spec>, t: Throwable) {
                        Log.e("SPEC", t.message)
                    }

                    override fun onResponse(call: Call<Spec>, response: Response<Spec>) {
                        spec = response.body()

                        Log.d("SPEC", "category: " + spec?.category)
                        Log.d("SPEC", "activityName:" + spec?.activityName)

                        get_txt.setText(spec?.category.toString())
                        get_txt2.append(spec?.activityName.toString())

                    }

                })
        }
    }
}
