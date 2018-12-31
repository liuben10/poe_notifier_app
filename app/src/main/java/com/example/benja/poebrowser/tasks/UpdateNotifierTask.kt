package com.example.benja.poebrowser.tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.services.WebListener

class UpdateNotifierTask(val context: Context) : AsyncTask<Map<String, String>, Integer, String>() {

    val url = "https://www.pathofexile.com/api/public-stash-tabs?id=306852701-317879848-299770855-343579402-324872552"

    val checkForCurrentChangeId: WebListener = {
        next_change_id ->
        run {
            Log.i("UpdateNotifierTask", "Response=$next_change_id")
            PoeAppContext.getPublicStashChecker(context).pullAndFilterItems(next_change_id)
        }
    }

    override fun doInBackground(vararg p0: Map<String, String>?): String {
//        checkLocal()
//        return ""
        val checker = PoeAppContext.getPoeNinjaChecker(context)
        checker.listeners.add(checkForCurrentChangeId)
        val next_change_id = checker.checkPoeNinja()
        return "Running"
    }

    fun checkLocal(): String {
        val filterChecker = PoeAppContext.getPublicStashChecker(context)
        val checked = filterChecker.pullAndFilterItems("313919620-325073444-306612393-351562971-332205554")
        return "Checking Was Executed..."
    }
}