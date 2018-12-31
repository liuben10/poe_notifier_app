package com.example.benja.poebrowser.services

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Klaxon
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.model.PoeItem

class PoeItemFilterServiceChecker(
        val context: Context
) {
    val url = "http://10.0.2.2:8080/public_stash_items"
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
                },
                Response.ErrorListener { res ->
                    Log.e("Poe-Stash-Filter", "Error, failed $res")
                }
        )

        stringRequest.retryPolicy = DefaultRetryPolicy(
                1000 * 30,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        PoeAppContext.getRequestQueue(context).add(stringRequest)
    }

    private fun parseChanges(raw: String): List<PoeItem> {
        val parsed = klaxon.parseArray<PoeItem>(raw)!!
        return parsed
    }

    private fun constructRequesturl(next_check_id: String): String {
        return this.url + "?id=" + next_check_id + "&shouldFilter=true"
    }
}