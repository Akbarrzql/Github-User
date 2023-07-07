package com.example.githubuserdicodingbfaa.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdicodingbfaa.model.ResponseDetailUsers
import com.example.githubuserdicodingbfaa.utils.Contans
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel: ViewModel() {

    val listDetailUsers: MutableLiveData<ResponseDetailUsers> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

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