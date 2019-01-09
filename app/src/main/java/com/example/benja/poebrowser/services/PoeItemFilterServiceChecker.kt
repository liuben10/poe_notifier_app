package com.example.benja.poebrowser.services

import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.example.benja.poebrowser.*
import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilterContainer
import com.google.gson.Gson
import java.util.*

const val ITEMS_LABEL: String = "ITEMS"
class PoeItemFilterServiceChecker(
        val context: Context
) {
//    val url = "http://10.0.2.2:8080/public_stash_items"
    val url = "https://codathon-188102.appspot.com/public_stash_items"
    val gson: Gson = Gson()
    val dao: PoeItemDao = PoeAppContext.getItemsDao(this.context!!)

    fun pullAndFilterItems(next_check_id: String) {
        val stashFilterServiceUrl = constructRequesturl(next_check_id)
        val filters = itemFiltersForTest()
        if (filters == null) {
            Log.d("Poe-Stash-Filter", "No filters found, not querying")
            return
        }
        val stringRequest = PostStringRequest(
                Request.Method.POST,
                stashFilterServiceUrl,
                Response.Listener { res ->
                    val poeItems = PoeItemParser.parseChanges(res)
                    Log.i("Poe-Stash-Filter", "Was able to parse item list")
                    Log.i("Poe-Stash-Filter", "Found Items=$poeItems")
                    if (poeItems.items_by_stash.isNotEmpty()) {
                        val assocedItems = assocSellerToItem(poeItems)
//                        sendNotification(poeItems, next_check_id)
                        sendBroadcast(assocedItems)
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

    private fun assocSellerToItem(stashes: PoeItemFilterContainer): List<PoeItem> {
        val flattened = mutableListOf<PoeItem>()
        for (stash in stashes.items_by_stash) {
            val accountName = stash.accountName
            for (item in stash.items) {
                item.seller = accountName
                dao.save(item)
                flattened.add(item)
            }
        }
        return flattened
    }


    private fun sendBroadcast(assocedItems: List<PoeItem>) {
        val intentToBroadcast = Intent(ITEMS_DETECTED_SIGNAL)
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

    private fun constructRequesturl(next_check_id: String): String {
        return this.url + "?id=" + next_check_id + "&shouldFilter=true&num_stashes=2"
    }

    private fun itemFiltersForTest(): String? {
        val allFilters = PoeAppContext.getPoeItemFilterDao(this.context).allFiltersList()
        if (allFilters.size > 0) {
            return gson.toJson(allFilters)
        }
        return null
    }
}