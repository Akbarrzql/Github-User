package com.example.githubuserdicodingbfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.databinding.CardListUsersBinding
import com.example.githubuserdicodingbfaa.model.response.ItemsItem

class SearchAdapter(private val searchResult: List<ItemsItem>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private lateinit var onItemClickCallback: SearchAdapter.OnItemClickCallback

    class MyViewHolder(var binding: CardListUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CardListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = searchResult[position]
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(user.avatarUrl)
                .into(civUser)
            tvName.text = user.login
            root.setOnClickListener { onItemClickCallback.onItemClicked(searchResult[position]) }
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}
