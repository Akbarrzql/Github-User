package com.example.githubuserdicodingbfaa.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdicodingbfaa.adapter.SearchAdapter
import com.example.githubuserdicodingbfaa.adapter.UsersAdapter
import com.example.githubuserdicodingbfaa.databinding.ActivityMainBinding
import com.example.githubuserdicodingbfaa.model.ItemsItem
import com.example.githubuserdicodingbfaa.model.ResponseUsersItem
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.viewmodel.MainViewModel
import com.example.githubuserdicodingbfaa.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        mainViewModel.users()
        mainViewModel.listUsers.observe(this) { listUsers ->
            setDataUserRv(listUsers)
        }

        mainViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar)
        }

        searchViewModel.searchResult.observe(this) { searchResult ->
            setSearchResultRv(searchResult)
        }

        searchViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isNotEmpty()) {
                        searchViewModel.searchUsers(s.toString())
                    } else {
                        searchViewModel.searchResult.value = emptyList()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }
            })
        }

    }

    private fun setDataUserRv(listUsers: List<ResponseUsersItem>) {
        val usersAdapter = UsersAdapter(listUsers)
        binding.rvUser.adapter = usersAdapter
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.layoutManager = layoutManager

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResponseUsersItem) {
                Intent (this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })
    }

    private fun setSearchResultRv(searchResult: List<ItemsItem>) {
        val searchAdapter = SearchAdapter(searchResult)
        if (searchResult.isEmpty()) {
            binding.lottieNotFound.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
        } else {
            binding.lottieNotFound.visibility = View.GONE
            binding.rvSearch.visibility = View.VISIBLE
            binding.rvSearch.adapter = searchAdapter
            val layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvSearch.layoutManager = layoutManager

            searchAdapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    Intent (this@MainActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                    Toast.makeText(this@MainActivity, data.login, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
