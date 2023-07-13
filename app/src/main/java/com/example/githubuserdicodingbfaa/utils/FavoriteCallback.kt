package com.example.githubuserdicodingbfaa.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserdicodingbfaa.model.database.FavoriteUser

class FavoriteCallback(private val mOldFavList: List<FavoriteUser>, private val mNewFavList: List<FavoriteUser>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavList = mOldFavList[oldItemPosition]
        val newFavList = mNewFavList[newItemPosition]
        return oldFavList.id == newFavList.id && oldFavList.login == newFavList.login
    }


}