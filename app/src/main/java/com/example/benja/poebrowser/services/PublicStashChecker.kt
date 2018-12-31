package com.example.benja.poebrowser.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.benja.poebrowser.model.Filter
import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PublicStashChanges


class PublicStashChecker(
        context: Context
) {
    val searchFilters: MutableList<Filter> = mutableListOf()
    val queue = Volley.newRequestQueue(context)
    val url = "http://localhost:8080/public_stash_items?"
    val klaxon: Klaxon = Klaxon()

    fun pullAndFilterItems(next_check_id: String):  List<PoeItem> {
        val next_block_url = constructRequesturl(next_check_id)
        val stringRequest = StringRequest(
                Request.Method.GET,
                next_block_url,
                Response.Listener<String> { res ->
                    val nextChangeSet = parseChanges(res)
                    print("Was able to parse the Next Change Set")
                },
                Response.ErrorListener { res ->
                    print("Error, failed {}".format(res))
                }
        )

        queue.add(stringRequest)
        return mutableListOf()
    }

    private fun parseChanges(raw: String): PublicStashChanges {
        val parsed = klaxon.parse<PublicStashChanges>(raw)!!
        return parsed
    }

    private fun constructRequesturl(next_check_id: String): String {
        return this.url + "id=" + next_check_id
    }

    fun withFilter(filter: Filter): PublicStashChecker {
        this.searchFilters.add(filter)
        return this
    }
}