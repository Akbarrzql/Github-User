package com.example.githubuserdicodingbfaa.view.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserdicodingbfaa.databinding.ActivitySplashScreenBinding
import com.example.githubuserdicodingbfaa.view.home.MainActivity
import com.example.githubuserdicodingbfaa.view.settings.SettingPreferences
import com.example.githubuserdicodingbfaa.view.settings.dataStore
import com.example.githubuserdicodingbfaa.viewmodel.MainViewModel
import com.example.githubuserdicodingbfaa.viewmodel.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        settingPreferencesMain()

        binding.lottieSplashScreen.alpha = 0f
        binding.lottieSplashScreen.animate().setDuration(6500).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }

    private fun settingPreferencesMain() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModelFactory = ViewModelFactory(pref)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getThemeSettings().observe(this) { isLightModeActive: Boolean ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}