package com.example.benja.poebrowser.services

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class PostStringRequest(
        method: Int,
        url: String?,
        listener: Response.Listener<String>?,
        errorListener: Response.ErrorListener?,
        private val body: String,
        private val providedHeaders: MutableMap<String, String> = mutableMapOf()
) : StringRequest(method, url, listener, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return providedHeaders
    }

    override fun getBody(): ByteArray? {
        return body!!.toByteArray()
    }
}