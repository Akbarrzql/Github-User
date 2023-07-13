package com.example.githubuserdicodingbfaa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null

        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserRoomDatabase::class.java,
                    "user_favorite_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
