package com.example.githubuserdicodingbfaa.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(user: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
    fun getUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun getUserById(id: Int): LiveData<FavoriteUser>
}
