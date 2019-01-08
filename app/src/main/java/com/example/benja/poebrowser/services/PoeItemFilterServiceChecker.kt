package com.example.benja.poebrowser.services

import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.example.benja.poebrowser.CHANNEL_ID
import com.example.benja.poebrowser.ITEMS_DETECTED_SIGNAL
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.R
import com.example.benja.poebrowser.model.PoeItemFilter
import com.example.poe_app_kt.model.PoeItemFilterContainer
import com.example.poe_app_kt.model.PoeModStringItemFilter
import com.google.gson.Gson
import java.util.*

const val ITEMS_LABEL: String = "ITEMS"
class PoeItemFilterServiceChecker(
        val context: Context
) {
    val url = "http://10.0.2.2:8080/public_stash_items"
//    val url = "https://codathon-188102.appspot.com/public_stash_items"
    val gson: Gson = Gson()

    fun pullAndFilterItems(next_check_id: String) {
        val stashFilterServiceUrl = constructRequesturl(next_check_id)
        val filters = itemFiltersForTest()
        val stringRequest = PostStringRequest(
                Request.Method.POST,
                stashFilterServiceUrl,
                Response.Listener { res ->
                    val poeItems = parseChanges(res)
                    Log.i("Poe-Stash-Filter", "Was able to parse item list")
                    Log.i("Poe-Stash-Filter", "Found Items=$poeItems")
                    if (poeItems.items_by_stash.isNotEmpty()) {
                        sendNotification(poeItems, next_check_id)
                        sendBroadcast(poeItems)
                    }
                },
                Response.ErrorListener { res ->
                    Log.e("Poe-Stash-Filter", "Error, failed $res")
                },
                filters,
                mutableMapOf(
                    "Content-Type" to "application/json"
                )
        )

        stringRequest.retryPolicy = DefaultRetryPolicy(
                1000 * 60,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        PoeAppContext.getRequestQueue(context).add(stringRequest)
    }


    private fun sendBroadcast(stashes: PoeItemFilterContainer) {
        val intentToBroadcast = Intent(ITEMS_DETECTED_SIGNAL)
        val message = StringBuilder()
        for(stash in stashes.items_by_stash) {
            message.append("\n++++++++++\n").append("\n")
            message.append("accountName=${stash.accountName}\n")
            message.append("stash=${stash.id}\n")
            for (item in stash.items) {
                message.append("\n==========\n")
                        .append(item.toPrettyString())
            }
        }

        intentToBroadcast.putExtra(ITEMS_LABEL, message.toString())
        context.sendBroadcast(intentToBroadcast)
    }

    fun sendNotification(stashes: PoeItemFilterContainer, next_check_id: String) {
        val detectedItems = StringBuilder()
        for (stash in stashes.items_by_stash) {
            detectedItems.append(stash.accountName).append("[")
            for(item in stash.items) {
                detectedItems.append(item.name).append(", ")
            }
            detectedItems.append("]\n")
        }

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Detected Items Based on your Filters in NextChangeId={${next_check_id}}")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(detectedItems.toString()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(UUID.randomUUID().mostSignificantBits.toInt(), mBuilder.build())
        }
    }

    private fun parseChanges(raw: String): PoeItemFilterContainer {
        val parsed = gson.fromJson<PoeItemFilterContainer>(raw, PoeItemFilterContainer::class.java)!!
        return parsed
    }

    private fun constructRequesturl(next_check_id: String): String {
        return this.url + "?id=" + next_check_id + "&shouldFilter=true"
    }

    private fun itemFiltersForTest(): String {
//        val loreweaveFilter = PoeItemFilter("Loreweave Filter", "Betrayal")
//        loreweaveFilter.name = "Loreweave"
        val energyShieldFilter = PoeItemFilter("Energy Shield Filter", "Betrayal")
        energyShieldFilter.explicitMods.add(PoeModStringItemFilter("^\\+(.+) to maximum Energy Shield$", 10))
//        energyShieldFilter.explicitMods.add(PoeModStringItemFilter("^(.+)% increased Energy Shield$", 10))
        return gson.toJson(mutableListOf(energyShieldFilter))
    }
}