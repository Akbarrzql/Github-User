package com.example.githubuserdicodingbfaa.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.R
import com.example.githubuserdicodingbfaa.adapter.SectionsPagerAdapter
import com.example.githubuserdicodingbfaa.databinding.ActivityDetailBinding
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser
import com.example.githubuserdicodingbfaa.model.response.ResponseDetailUsers
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.viewmodel.DetailUsersViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailUsersViewModel: DetailUsersViewModel
    private var detailUser = ResponseDetailUsers()
    private val helper = Helper()

    private var buttonState: Boolean = false
    private var favoriteUser: FavoriteUser? = null

    companion object {
        const val EXTRA_USERNAME = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailUsersViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(DetailUsersViewModel::class.java)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        detailUsersViewModel.detailUsers(username.toString())

        val actionBar = supportActionBar
        actionBar?.apply {
            title = username
            setDisplayHomeAsUpEnabled(true)
        }

        detailUsersViewModel.listDetailUsers.observe(this) { detailList ->
            setDataToView(detailList)
        }

        detailUsersViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar2)
        }

        setViewPager()
        checkFavoriteState()
        addFavoriteFab()
    }

    private fun setDataToView(detailList: ResponseDetailUsers) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(detailList.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgDetail)
            tvFullname.text = detailList.name ?: "Tidak ada nama."
            tvUsername.text = detailList.login ?: "Tidak ada username."
            tvFollowers.text = detailList.followers.toString()
            tvFollowing.text = detailList.following.toString()
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, intent.extras!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()
    }

    private fun checkFavoriteState() {
        detailUsersViewModel.listDetailUsers.observe(this) { detailList ->
            detailUser = detailList
            setDataToView(detailUser)
            favoriteUser = detailUser.id?.let { FavoriteUser(it, detailUser.login) }
            detailUsersViewModel.getAllFavorites().observe(this) { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.id == data.id) {
                            buttonState = true
                            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        }
                    }
                }
            }
        }
    }

    private fun addFavoriteFab(){
        binding.fabFavorite.setOnClickListener {
            if (buttonState) {
                buttonState = false
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                deleteFavorite()
            } else {
                buttonState = true
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                insertFavorite(detailUser)
            }
        }
    }

    private fun insertFavorite(detailList: ResponseDetailUsers) {
        val favoriteUser = detailUser.id?.let {
            FavoriteUser(
                it.toInt(),
                detailList.login.toString(),
                detailList.avatarUrl.toString()
            )
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                detailUsersViewModel.insert(favoriteUser as FavoriteUser)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "Menambahkan ke favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteFavorite() {
        detailUser.id?.let {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    detailUsersViewModel.delete(it)
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetailActivity, "Menghapus dari favorit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail, menu)
        val isDarkModeActive = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val menuItem = menu?.findItem(R.id.share)
        if (isDarkModeActive) {
            menuItem?.icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_share_white_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_share_24)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.share -> {
                val username = intent.getStringExtra(EXTRA_USERNAME)
                Intent().apply {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "https://github.com/$username")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}