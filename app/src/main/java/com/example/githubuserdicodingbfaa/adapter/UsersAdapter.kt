package com.example.githubuserdicodingbfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.databinding.CardListUsersBinding
import com.example.githubuserdicodingbfaa.model.ResponseUsersItem

class UsersAdapter(private val listUsers: List<ResponseUsersItem>): RecyclerView.Adapter<UsersAdapter.MyViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(var binding: CardListUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CardListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = listUsers[position]
        holder.binding.apply {
            tvName.text = user.login
            Glide.with(holder.itemView)
                .load(user.avatarUrl)
                .into(civUser)
            root.setOnClickListener { onItemClickCallback.onItemClicked(listUsers[position]) }
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseUsersItem)
    }
}