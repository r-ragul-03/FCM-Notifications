package com.app.fcmNotifications

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var editTitle: EditText? = null
    var editMessage: EditText? = null

    private val fcmAPI = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + "Your FCM Server Key"
    private val contentType = "application/json"
    val tag = "NOTIFICATION TAG"

    private var notificationTitle: String? = null
    private var notificationMessage: String? = null
    private var topic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTitle = findViewById(R.id.editTitle)
        editMessage = findViewById(R.id.editBody)
        val btnSend = findViewById<Button>(R.id.sendButton)

        btnSend.setOnClickListener {
            topic = "/topics/userId"
            notificationTitle = editTitle?.text.toString()
            notificationMessage = editMessage?.text.toString()

            val notification = JSONObject()
            val notifcationBody = JSONObject()
            try {
                notifcationBody.put("title", notificationTitle)
                notifcationBody.put("message", notificationMessage)
                notification.put("to", topic)
                notification.put("data", notifcationBody)
            } catch (e: JSONException) {
                Log.e(tag, "onCreate: " + e.message)
            }
            sendNotification(notification)
        }
    }

    private fun sendNotification(notification: JSONObject) {
        val request = object : JsonObjectRequest(
            Method.POST,fcmAPI, notification,
            Response.Listener { response ->
                Log.i(tag, "onResponse: $response")
                editTitle!!.setText("")
                editMessage!!.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(this@MainActivity, "Problem in sending Notification", Toast.LENGTH_LONG).show()
                Log.i(tag, "onErrorResponse: Didn't work")
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
        VolleySingleton(this).addToRequestQueue(request)
    }
}