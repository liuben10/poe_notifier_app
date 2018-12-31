package com.example.benja.poebrowser

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.benja.poebrowser.tasks.UpdateNotifierTask
const val CHANNEL_ID: String = "POE_ITEMS_NOTIFICATION"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
