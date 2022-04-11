package com.nmssalman.qualitapps.Utilities

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MyApplication: Application() {
    private var mRequestQueue: RequestQueue? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        requestQueue!!.add(req)
    }

    fun pauseRequestQueue() {
        mRequestQueue!!.stop()
    }

    fun resumeRequestQueue() {
        mRequestQueue!!.start()
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }
    private val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue
        }

    companion object{
        val TAG = MyApplication::class.java.simpleName

        @get:Synchronized
        var instance: MyApplication? = null
            private set

        val moshi: Moshi by lazy {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(NullToDoubleAdapter())
                .add(NullToStringAdapter())
                .add(NullToIntAdapter())
                .build()
        }
    }
}