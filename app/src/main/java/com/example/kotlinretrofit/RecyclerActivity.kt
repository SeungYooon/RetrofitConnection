package com.example.kotlinretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.dto.*
import com.example.kotlinretrofit.service.ApiClient
import com.example.kotlinretrofit.service.RetrofitInterface
import com.example.kotlinretrofit.util.RecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerActivity : AppCompatActivity(), RecyclerAdapter.ClickListener {

    var items: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(items, this)

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val retrofitInterface: RetrofitInterface =
            ApiClient().getApiClient()!!.create(RetrofitInterface::class.java)

        retrofitInterface.api().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>, response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful()) {
                    items = response.body()!!
                    recyclerView.adapter = RecyclerAdapter(response.body()!!, this@RecyclerActivity)
                    Log.d("###########", response.message())
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("@@@@@@@@@@@@@@@@", t.message)
            }
        })
    }

    override fun getItem(position: Int) {
        val alertDialog = AlertDialog.Builder(this@RecyclerActivity)
        alertDialog.setTitle(items.get(position).email)
        alertDialog.setMessage(items.get(position).first_name)
        alertDialog.setPositiveButton("Ok") { dialog, which ->
            Toast.makeText(this@RecyclerActivity, "Ok", Toast.LENGTH_LONG).show()
        }
        alertDialog.show()
    }

}

