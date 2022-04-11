package com.nmssalman.qualitapps.Utilities

import android.app.AlertDialog
import android.content.Context
import com.nmssalman.qualitapps.*
object DialogBuilder {
    fun dialogBuilder(message: String, context: Context): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_error_outline_24)
            .setPositiveButton(R.string.message_ok, null)
            .setCancelable(false)
        return builder
    }
}