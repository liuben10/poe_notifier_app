package com.example.benja.poebrowser

import android.os.AsyncTask
import com.example.benja.poebrowser.model.PoeItem

class PublicStashCheckTask : AsyncTask<Map<String, String>, Long, List<PoeItem>>() {

    override fun doInBackground(vararg p0: Map<String, String>?): List<PoeItem> {
        // TODO
        return mutableListOf()
    }
}