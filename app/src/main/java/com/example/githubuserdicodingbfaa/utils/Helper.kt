package com.example.githubuserdicodingbfaa.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.githubuserdicodingbfaa.R

class Helper {
    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

}