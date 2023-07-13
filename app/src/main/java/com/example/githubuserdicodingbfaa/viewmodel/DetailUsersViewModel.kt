package com.example.githubuserdicodingbfaa.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserdicodingbfaa.data.FavoriteUserRoomDatabase
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser
import com.example.githubuserdicodingbfaa.model.response.ResponseDetailUsers
import com.example.githubuserdicodingbfaa.repository.FavoriteUserRepository
import com.example.githubuserdicodingbfaa.utils.Contans
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel(application: Application) : AndroidViewModel(application) {

    val listDetailUsers: MutableLiveData<ResponseDetailUsers> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val mFavoriteUserRepository: FavoriteUserRepository

    init {
        val favoriteUserDao = FavoriteUserRoomDatabase.getDatabase(application).favoriteUserDao()
        mFavoriteUserRepository = FavoriteUserRepository(favoriteUserDao)
    }

    fun insert(user: FavoriteUser) {
        viewModelScope.launch {
            mFavoriteUserRepository.insertFavorite(user)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            mFavoriteUserRepository.deleteFavorite(id)
        }
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()

    fun detailUsers(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(Contans.tokenGithub, username)
        client.enqueue(object : Callback<ResponseDetailUsers> {
            override fun onResponse(
                call: Call<ResponseDetailUsers>,
                response: Response<ResponseDetailUsers>
            ) {
                if (response.isSuccessful) {
                    listDetailUsers.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseDetailUsers>, t: Throwable) {
                isLoading.value = false
                Log.e("Failure", t.message.toString())
            }
        })
    }
}
