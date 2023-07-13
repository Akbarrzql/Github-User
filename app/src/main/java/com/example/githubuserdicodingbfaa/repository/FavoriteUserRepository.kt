package com.example.githubuserdicodingbfaa.repository

import androidx.lifecycle.LiveData
import com.example.githubuserdicodingbfaa.data.FavoriteUserDao
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteUserRepository(private val favoriteUserDao: FavoriteUserDao) {

    fun getAllFavorites(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getUser()
    }

    suspend fun insertFavorite(user: FavoriteUser) {
        withContext(Dispatchers.IO) {
            favoriteUserDao.insertFavorite(user)
        }
    }

    suspend fun deleteFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            favoriteUserDao.delete(id)
        }
    }

}

