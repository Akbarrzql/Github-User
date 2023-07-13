package com.example.githubuserdicodingbfaa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.databinding.CardListUsersBinding
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser
import com.example.githubuserdicodingbfaa.utils.FavoriteCallback
import com.example.githubuserdicodingbfaa.view.home.DetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private lateinit var onItemClickCallback: FavoriteUserAdapter.OnItemClickCallback
    private val listFavorites = ArrayList<FavoriteUser>()

    fun setFavorites(favorites: List<FavoriteUser>) {
        val diffCallback = FavoriteCallback(this.listFavorites, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteUserViewHolder(private val binding: CardListUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteUser) {
            with(binding) {
                tvName.text = favorites.login
                Glide.with(itemView)
                    .load(favorites.avatarUrl)
                    .into(civUser)
                root.setOnClickListener {
                    val onItemClickCallback = object : OnItemClickCallback {
                        override fun onItemClicked(data: FavoriteUser) {
                            val intent = Intent(itemView.context, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_USERNAME, favorites.login)
                            itemView.context.startActivity(intent)
                        }
                    }
                    onItemClickCallback.onItemClicked(favorites)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val itemRowUserBinding = CardListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favorites = listFavorites[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = listFavorites.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }
}