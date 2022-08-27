package com.app.fcmNotifications

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton(ctx: Context) {
    private var requestQueue: RequestQueue
    private var context: Context

    init {
        context = ctx
        requestQueue = getRequestQueue()
    }


    private fun getRequestQueue(): RequestQueue {
        requestQueue = Volley.newRequestQueue(context.applicationContext)
        return requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        getRequestQueue().add(req)
    }
}
