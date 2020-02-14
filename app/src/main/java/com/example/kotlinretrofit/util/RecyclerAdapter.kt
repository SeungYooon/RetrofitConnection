package com.example.kotlinretrofit.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinretrofit.R
import com.example.kotlinretrofit.dto.User
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter(var items: ArrayList<User>, var itemClick: ClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    interface ClickListener {
        fun getItem(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }


    class ViewHolder(itemView: View, var itemClick: ClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val context = itemView.context

        private val imageView = itemView.imageView
        private val id = itemView.id_txt
        private val email = itemView.email_txt
        private val FisrtName = itemView.first_name_txt
        private val lastName = itemView.last_name_txt

        fun bindData(items: User) {
            id.text = items.id.toString()
            email.text = items.email
            FisrtName.text = items.first_name
            lastName.text = items.last_name
            Glide.with(context).load(items.avatar)
                .apply(RequestOptions().centerCrop()).into(imageView)

            itemView.setOnClickListener(View.OnClickListener {
                itemClick.getItem(adapterPosition)
            })

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view, itemClick)
    }
}