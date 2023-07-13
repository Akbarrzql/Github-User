package com.example.githubuserdicodingbfaa.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null
)
