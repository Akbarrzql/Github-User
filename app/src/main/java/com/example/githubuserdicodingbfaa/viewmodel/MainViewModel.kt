package com.example.githubuserdicodingbfaa.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdicodingbfaa.model.ResponseUsersItem
import com.example.githubuserdicodingbfaa.utils.Contans.Companion.tokenGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val listUsers: MutableLiveData<List<ResponseUsersItem>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun users() {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(tokenGithub)
        client.enqueue(object : Callback<List<ResponseUsersItem>> {
            override fun onResponse(
                call: Call<List<ResponseUsersItem>>,
                response: Response<List<ResponseUsersItem>>
            ) {
                if (response.isSuccessful) {
                    listUsers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseUsersItem>>, t: Throwable) {
                isLoading.value = false
                Log.e("Failure", t.message.toString())
            }

        })
    }
}
