package com.example.githubuserdicodingbfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.databinding.CardListUsersBinding
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowersItem


class FollowerAdapter(private val listFollower: List<ResponseFollowersItem>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: FollowerAdapter.OnItemClickCallback

    class ViewHolder(var binding: CardListUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        holder.binding.apply {
            tvName.text = follower.login
            Glide.with(holder.itemView)
                .load(follower.avatarUrl)
                .into(civUser)
            root.setOnClickListener { onItemClickCallback.onItemClicked(listFollower[position]) }
        }
    }

    override fun getItemCount(): Int = listFollower.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseFollowersItem)
    }

}