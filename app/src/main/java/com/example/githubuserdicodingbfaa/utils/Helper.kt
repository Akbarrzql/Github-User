package com.example.githubuserdicodingbfaa.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

class Helper {
    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}