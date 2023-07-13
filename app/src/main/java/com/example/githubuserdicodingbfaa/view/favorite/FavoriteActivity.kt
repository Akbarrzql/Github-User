package com.example.githubuserdicodingbfaa.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdicodingbfaa.R
import com.example.githubuserdicodingbfaa.adapter.FavoriteUserAdapter
import com.example.githubuserdicodingbfaa.databinding.ActivityFavoriteBinding
import com.example.githubuserdicodingbfaa.viewmodel.FavoriteUserViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.apply {
            title = "User Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = FavoriteUserAdapter()
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.setHasFixedSize(false)
        binding.rvFavorites.adapter = adapter
        favoriteUserViewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)
        favoriteUserViewModel.allFavorites.observe(this) { favorites ->
            favorites.let {
                adapter.setFavorites(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}