package com.example.benja.poebrowser

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.benja.poebrowser.services.PoeItemFilterServiceChecker
import com.example.benja.poebrowser.services.PoeNinjaChecker

class PoeAppContext {
    companion object {

        @Volatile
        private var requestQueue: RequestQueue? = null

        // Tightly Bound to a particular context, should decouple more but eh.
        fun getRequestQueue(context: Context): RequestQueue {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context)
            }
            return checkNotNull(requestQueue)
        }

        @Volatile
        private var poeItemFilterServiceChecker: PoeItemFilterServiceChecker? = null

        fun getPublicStashChecker(context: Context): PoeItemFilterServiceChecker {
            if (poeItemFilterServiceChecker == null) {
                poeItemFilterServiceChecker = PoeItemFilterServiceChecker(context)
            }
            return checkNotNull(poeItemFilterServiceChecker)
        }

        @Volatile
        private var poeNinjaChecker: PoeNinjaChecker? = null

        fun getPoeNinjaChecker(context: Context): PoeNinjaChecker {
            if (poeNinjaChecker == null) {
                poeNinjaChecker = PoeNinjaChecker(context)
            }
            return checkNotNull(poeNinjaChecker)
        }
    }
}