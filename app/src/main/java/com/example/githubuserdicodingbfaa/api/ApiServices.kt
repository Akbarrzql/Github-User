package com.example.githubuserdicodingbfaa.api

import com.example.githubuserdicodingbfaa.model.response.ResponseDetailUsers
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowersItem
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowingItem
import com.example.githubuserdicodingbfaa.model.response.ResponseSearch
import com.example.githubuserdicodingbfaa.model.response.ResponseUsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String,
    ): Call<List<ResponseUsersItem>>

    @GET("search/users")
    fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") username: String
    ): Call<ResponseSearch>

    @GET("users/{username}")
    fun getDetailUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ResponseDetailUsers>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<List<ResponseFollowersItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<List<ResponseFollowingItem>>
}