package com.example.benja.poebrowser.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.example.benja.poebrowser.PoeAppContext

typealias WebListener = (String) -> Unit

class PoeNinjaChecker(
        val context: Context
) {
    val url = "https://poe.ninja/api/Data/GetStats"

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
        val struct = Klaxon().parse<PoeNinjaIdContainer>(raw)
        return struct!!.nextChangeId
    }
}

data class PoeNinjaIdContainer (
    @Json(name="next_change_id")
    val nextChangeId: String
)