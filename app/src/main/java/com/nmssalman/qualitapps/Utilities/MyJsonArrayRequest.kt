package com.nmssalman.qualitapps.Utilities

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

open class MyJsonArrayRequest(method: Int, url: String, jsonObject: JSONArray?,
                         response: Response.Listener<JSONArray>, error: Response.ErrorListener,
                         val applicationContext: Context
): JsonArrayRequest(method, url, jsonObject, response, error) {

    init {
        retryPolicy = DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }

    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        headers["X-Requested-With"] = "XMLHttpRequest"
        headers["Content-Type"] = "application/json"
        return headers
    }
}