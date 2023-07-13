package com.example.githubuserdicodingbfaa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserdicodingbfaa.data.FavoriteUserRoomDatabase
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser
import com.example.githubuserdicodingbfaa.repository.FavoriteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteUserRepository: FavoriteUserRepository
    val allFavorites: LiveData<List<FavoriteUser>>

    init {
        val favoriteUserDao = FavoriteUserRoomDatabase.getDatabase(application).favoriteUserDao()
        favoriteUserRepository = FavoriteUserRepository(favoriteUserDao)
        allFavorites = favoriteUserRepository.getAllFavorites()
    }

    fun insertFavorite(user: FavoriteUser) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUserRepository.insertFavorite(user)
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUserRepository.deleteFavorite(id)
        }
    }
}


