package com.nmssalman.qualitapps.Utilities

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.VolleyError
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.json.JSONObject

open class MyRepository (private val applicationContext: Context, private val moshi: Moshi) {

    protected var scopeToUse : CoroutineScope = GlobalScope  //I feel like coroutines with Volley is messed, need to make this better

    private val errorRegex: Regex = "\\[\"([\\w\\d\\s.!?\\\\-]*)\"".toRegex()

    private val _showNetworkDialog = MutableLiveData<Boolean>()
    val showNetworkDialog : LiveData<Boolean>
        get() = _showNetworkDialog

    private val _networkErrorMessage = SingleLiveEvent<String>()
    val networkErrorMessage : LiveData<String>
        get() = _networkErrorMessage

    private val _networkSuccessMessage = SingleLiveEvent<Int>()
    val networkSuccessMessage : LiveData<Int>
        get() = _networkSuccessMessage

    protected fun displayNetworkMessage(message: Int) {
        _networkSuccessMessage.postValue(message)
    }

    protected fun displayNetworkError(error: String) {
        Log.i("ErrorToDisplay", error)
        val result = checkErrorRegex(error)
        if(result.isNotEmpty())
            _networkErrorMessage.postValue(result)
        else
            _networkErrorMessage.postValue(error)
    }

    protected fun displayNetworkDialog(show: Boolean) {
        _showNetworkDialog.postValue(show)
    }

    private fun checkErrorRegex(errorData: String): String {
        return errorRegex.find(errorData)?.groups?.get(1)?.value ?: ""
    }

    protected inner class MyVolleyError(private val task: VolleyTaskListener) : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            displayNetworkDialog(false)
            val response = error?.networkResponse
            Log.i("ErrorReceived", response?.data.toString())
            if (response?.data != null) {
                val errorData = String(response.data)
                val json = JSONObject(errorData)
                displayNetworkError(json.optString("message"))
            }
            else
                displayNetworkError("Something went wrong. please contact admin!")

        }
    }
    interface VolleyTaskListener {
        fun onFinished()
    }
}