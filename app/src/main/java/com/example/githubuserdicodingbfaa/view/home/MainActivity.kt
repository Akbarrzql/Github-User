package com.example.githubuserdicodingbfaa.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdicodingbfaa.R
import com.example.githubuserdicodingbfaa.adapter.SearchAdapter
import com.example.githubuserdicodingbfaa.adapter.UsersAdapter
import com.example.githubuserdicodingbfaa.databinding.ActivityMainBinding
import com.example.githubuserdicodingbfaa.model.response.ItemsItem
import com.example.githubuserdicodingbfaa.model.response.ResponseUsersItem
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.view.favorite.FavoriteActivity
import com.example.githubuserdicodingbfaa.view.settings.SettingPreferences
import com.example.githubuserdicodingbfaa.view.settings.SettingsActivity
import com.example.githubuserdicodingbfaa.view.settings.dataStore
import com.example.githubuserdicodingbfaa.viewmodel.MainViewModel
import com.example.githubuserdicodingbfaa.viewmodel.SearchViewModel
import com.example.githubuserdicodingbfaa.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private val searchViewModel by viewModels<SearchViewModel>()
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModelFactory = ViewModelFactory(pref)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        settingPreferencesMain()


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
            val menu = searchBar.menu
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.option_menu, menu)
            val isDarkModeActive = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val menuItem = menu.findItem(R.id.menu1)
            val menuItem2 = menu.findItem(R.id.menu2)
            if (isDarkModeActive) {
                menuItem.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_settings_white_24)
                menuItem2.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_favorite_white_24)
            } else {
                menuItem.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_settings_24)
                menuItem2.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_favorite_24)
            }
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        Intent(this@MainActivity, SettingsActivity::class.java).also { it ->
                            startActivity(it)
                        }
                        true
                    }
                    R.id.menu2 -> {
                        Intent(this@MainActivity, FavoriteActivity::class.java).also {
                            startActivity(it)
                        }
                        true
                    }
                    else -> false
                }
            }
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

    private fun settingPreferencesMain(){
        mainViewModel.getThemeSettings().observe(this) { isLightModeActive: Boolean ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val isDarkModeActive = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val menuItem = menu.findItem(R.id.menu1)
        if (isDarkModeActive) {
            menuItem.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_settings_white_24)
        } else {
            menuItem.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.baseline_settings_24)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
