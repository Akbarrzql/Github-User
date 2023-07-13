package com.example.githubuserdicodingbfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.databinding.CardListUsersBinding
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowingItem

class FollowingAdapter(private val listFollowing: List<ResponseFollowingItem>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: FollowingAdapter.OnItemClickCallback

    class ViewHolder(var binding: CardListUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollowing[position]

        holder.binding.apply {
            tvName.text = follower.login
            Glide.with(holder.itemView)
                .load(follower.avatarUrl)
                .into(civUser)
            root.setOnClickListener { onItemClickCallback.onItemClicked(listFollowing[position]) }
        }
    }

    override fun getItemCount(): Int = listFollowing.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseFollowingItem)
    }

}