package com.example.githubuserdicodingbfaa.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowingItem
import com.example.githubuserdicodingbfaa.utils.Contans
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    val listFollowingUsers: MutableLiveData<List<ResponseFollowingItem>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getFollowing(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(Contans.tokenGithub, username)
        client.enqueue(object : Callback<List<ResponseFollowingItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowingItem>>,
                response: Response<List<ResponseFollowingItem>>
            ) {
                if (response.isSuccessful) {
                    listFollowingUsers.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseFollowingItem>>, t: Throwable) {
                isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}