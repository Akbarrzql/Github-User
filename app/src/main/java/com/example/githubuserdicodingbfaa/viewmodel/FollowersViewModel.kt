package com.example.githubuserdicodingbfaa.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowersItem
import com.example.githubuserdicodingbfaa.utils.Contans.Companion.tokenGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    val listFollowersUsers: MutableLiveData<List<ResponseFollowersItem>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getFollower(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(tokenGithub, username)
        client.enqueue(object : Callback<List<ResponseFollowersItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowersItem>>,
                response: Response<List<ResponseFollowersItem>>
            ) {
                if (response.isSuccessful) {
                    listFollowersUsers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseFollowersItem>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}