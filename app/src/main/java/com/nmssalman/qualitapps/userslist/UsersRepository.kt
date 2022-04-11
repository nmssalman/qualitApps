package com.nmssalman.qualitapps.userslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.nmssalman.qualitapps.Utilities.MyApplication
import com.nmssalman.qualitapps.Utilities.MyJsonArrayRequest
import com.nmssalman.qualitapps.Utilities.MyRepository
import com.nmssalman.qualitapps.Utilities.URLHelper
import com.nmssalman.qualitapps.userslist.dataset.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class UsersRepository(val applicationContext: Context, val moshi: Moshi): MyRepository(applicationContext, moshi) {
    private val getUsersListApiTask = GetUsersListApiTask()

    private val _getUsersSuccess = MutableLiveData<List<User>>()
    val getUsersSuccess : LiveData<List<User>>
        get() = _getUsersSuccess

    fun getUsersList(){
        getUsersListApiTask.onFinished()
    }
    private inner class GetUsersListApiTask: VolleyTaskListener{
        override fun onFinished() {
            val URL = URLHelper.USERS
            val jsonArrayRequest = object : MyJsonArrayRequest(Method.GET, URL, null, Response.Listener {
                val type = Types.newParameterizedType(List::class.java, User::class.java)
                val usersList = moshi.adapter<List<User>>(type).fromJson(it.toString())
                if(usersList != null)
                    _getUsersSuccess.postValue(usersList!!)
            }, MyVolleyError(getUsersListApiTask), applicationContext){}
            MyApplication.instance?.addToRequestQueue(jsonArrayRequest)
        }
    }
}