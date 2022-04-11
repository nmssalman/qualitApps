package com.nmssalman.qualitapps.Utilities

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class Utils(private  val activity: Activity) {

    fun setTitleBar(title: String)
    {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    fun getString(id: Int): String
    {
        return activity.applicationContext.getString(id)
    }
}