package com.example.benja.poebrowser.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.benja.poebrowser.PoeAppContext
import com.google.gson.Gson

typealias WebListener = (String) -> Unit

class PoeNinjaChecker(
        val context: Context
) {
    val url = "https://poe.ninja/api/Data/GetStats"

    val gson = Gson()

    val listeners = arrayListOf<WebListener>()

    fun checkPoeNinja() {
        val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { res ->
                    val nextChangeId = extractNextChangeId(res)
                    this.listeners.forEach { listener -> listener(nextChangeId) }
                },
                Response.ErrorListener { res ->
                    print("Error, failed {}".format(res))
                }
        )
        PoeAppContext.getRequestQueue(context).add(stringRequest)
    }

    private fun extractNextChangeId(raw: String): String {
        val struct = gson.fromJson(raw, PoeNinjaIdContainer::class.java)
        return struct!!.next_change_id
    }
}

data class PoeNinjaIdContainer (
    val next_change_id: String
)