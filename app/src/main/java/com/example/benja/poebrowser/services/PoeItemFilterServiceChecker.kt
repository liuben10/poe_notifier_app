package com.example.benja.poebrowser.services

import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.example.benja.poebrowser.CHANNEL_ID
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.R
import com.example.benja.poebrowser.model.PoeItem
import java.util.*

const val ITEMS_DETECTED_SIGNAL: String = "ITEMS_DETECTED"
const val ITEMS_LABEL: String = "ITEMS"
class PoeItemFilterServiceChecker(
        val context: Context
) {
    val url = "https://codathon-188102.appspot.com/public_stash_items"
    val klaxon: Klaxon = Klaxon()

    fun pullAndFilterItems(next_check_id: String) {
        val stashFilterServiceUrl = constructRequesturl(next_check_id)
        val stringRequest = StringRequest(
                Request.Method.GET,
                stashFilterServiceUrl,
                Response.Listener<String> { res ->
                    val poeItems = parseChanges(res)
                    Log.i("Poe-Stash-Filter", "Was able to parse item list")
                    Log.i("Poe-Stash-Filter", "Found Items=$poeItems")
                    if (poeItems.size > 0) {
                        sendNotification(poeItems, next_check_id)
                        sendBroadcast(poeItems)
                    }
                },
                Response.ErrorListener { res ->
                    Log.e("Poe-Stash-Filter", "Error, failed $res")
                }
        )

        stringRequest.retryPolicy = DefaultRetryPolicy(
                1000 * 60,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        PoeAppContext.getRequestQueue(context).add(stringRequest)
    }

    private fun sendBroadcast(poeItems: List<PoeItem>) {
        val intentToBroadcast = Intent(ITEMS_DETECTED_SIGNAL)
        val message = StringBuilder()
        for (item in poeItems) {
            message.append("\n==========\n")
                    .append(item.toPrettyString())
        }
        intentToBroadcast.putExtra(ITEMS_LABEL, message.toString())
        context.sendBroadcast(intentToBroadcast)
    }

    fun sendNotification(items: List<PoeItem>, next_check_id: String) {
        val detectedItems = StringBuilder()
        detectedItems.append("---------")
        for(item in items) {
            detectedItems.append(item).append("\n")
        }
        var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Detected Items Based on your Filters in NextChangeId={${next_check_id}}")
                .setContentText(detectedItems.toString())
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(detectedItems.toString()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(UUID.randomUUID().mostSignificantBits.toInt(), mBuilder.build())
        }
    }

    private fun parseChanges(raw: String): List<PoeItem> {
        val parsed = klaxon.parseArray<PoeItem>(raw)!!
        return parsed
    }

    private fun constructRequesturl(next_check_id: String): String {
        return this.url + "?id=" + next_check_id + "&shouldFilter=true"
    }
}