package com.example.benja.poebrowser

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.benja.poebrowser.services.PoeItemFilterDao
import com.example.benja.poebrowser.services.PoeItemFilterServiceChecker
import com.example.benja.poebrowser.services.PoeNinjaChecker

class PoeAppContext {
    companion object {

        @Volatile
        private var handler: Handler? = null

        fun getHandler(): Handler {
            if (handler == null) {
                this.handler = Handler()
            }
            return checkNotNull(this.handler)
        }

        @Volatile
        private var poeAppItemFilterDaoDumb: PoeItemFilterDao? = null

        fun getPoeItemFilterDumbDao(context: Context): PoeItemFilterDao {
            if (poeAppItemFilterDaoDumb == null) {
                this.poeAppItemFilterDaoDumb = PoeItemFilterDao(context)
            }
            return checkNotNull(this.poeAppItemFilterDaoDumb)
        }

        @Volatile
        private var requestQueue: RequestQueue? = null

        // Tightly Bound to a particular context, should decouple more but eh.
        fun getRequestQueue(context: Context): RequestQueue {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context)
            }
            return checkNotNull(requestQueue)
        }

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var poeItemFilterServiceChecker: PoeItemFilterServiceChecker? = null

        fun getPublicStashChecker(context: Context): PoeItemFilterServiceChecker {
            if (poeItemFilterServiceChecker == null) {
                poeItemFilterServiceChecker = PoeItemFilterServiceChecker(context)
            }
            return checkNotNull(poeItemFilterServiceChecker)
        }

        @SuppressLint("StaticFieldLeak")
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