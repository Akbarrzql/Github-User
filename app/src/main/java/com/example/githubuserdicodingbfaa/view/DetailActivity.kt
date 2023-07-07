package com.example.githubuserdicodingbfaa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserdicodingbfaa.R
import com.example.githubuserdicodingbfaa.adapter.SectionsPagerAdapter
import com.example.githubuserdicodingbfaa.databinding.ActivityDetailBinding
import com.example.githubuserdicodingbfaa.model.ResponseDetailUsers
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.viewmodel.DetailUsersViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val userDetailViewModel by viewModels<DetailUsersViewModel>()
    private val helper = Helper()

    companion object {
        const val EXTRA_USERNAME = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        userDetailViewModel.detailUsers(username.toString())

        val actionBar = supportActionBar
        actionBar?.apply {
            title = username
            setDisplayHomeAsUpEnabled(true)
        }

        userDetailViewModel.listDetailUsers.observe(this) { detailList ->
            setDataToView(detailList)
        }

        userDetailViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar2)
        }

        setViewPager()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
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