package com.example.kotlinretrofit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PREFERENCE = "com.example.kotlinretrofit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pref = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        var users = pref.getString("username", "")

        hello.text = "환영합니다" + users + "님"
    }
}
