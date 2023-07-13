package com.example.githubuserdicodingbfaa.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdicodingbfaa.model.response.ItemsItem
import com.example.githubuserdicodingbfaa.model.response.ResponseSearch
import com.example.githubuserdicodingbfaa.utils.Contans.Companion.tokenGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val searchResult: MutableLiveData<List<ItemsItem>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun searchUsers(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(tokenGithub, username)
        client.enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                if (response.isSuccessful) {
                    val items = response.body()?.items
                    searchResult.value = items as List<ItemsItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                isLoading.value = false
                Log.e("Failure", t.message.toString())
            }

        })
    }
}

