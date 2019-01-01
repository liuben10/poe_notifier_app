package com.example.benja.poebrowser

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.benja.poebrowser.services.ITEMS_DETECTED_SIGNAL
import com.example.benja.poebrowser.services.ITEMS_LABEL
import com.example.benja.poebrowser.tasks.UpdateNotifierTask
import org.w3c.dom.Text

class ItemFilterListFragment : Fragment() {

    var handler: Handler? = null

    var receiver: BroadcastReceiver? = null

    class ItemFilterBroadCastReceiver(val viewToUpdate: TextView) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ITEMS_DETECTED_SIGNAL.equals(intent!!.action)) {
                val body = intent.getStringExtra(ITEMS_LABEL)
                viewToUpdate.text = body
            }
        }
    }

//    val url = "https://poe.ninja/api/Data/GetStats"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        handler = Handler()
        return inflater.inflate(R.layout.request_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        val contentBody: TextView = view!!.findViewById(R.id.render)
        val intentFilter = IntentFilter(ITEMS_DETECTED_SIGNAL)
        this.receiver = ItemFilterBroadCastReceiver(contentBody)
        this.activity!!.baseContext.registerReceiver(receiver, intentFilter)

        val runnable = updateNotify()
        runnable.run()

        createNotificationChannel()
    }

    override fun onStop() {
        super.onStop()
        if (this.receiver != null) {
            this.activity!!.baseContext.unregisterReceiver(this.receiver)
        }
    }

    fun updateNotify(): Runnable {
        return Runnable {
            val notifier = UpdateNotifierTask(activity!!)
            try {
                notifier.execute()
            } finally {
                handler!!.postDelayed(updateNotify(), 1000 * 240)
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}