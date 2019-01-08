package com.example.benja.poebrowser.services

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.benja.poebrowser.FilterContract
import com.example.benja.poebrowser.FilterDbHelper
import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PoeItemFilter
import com.google.gson.Gson

class PoeItemFilterDao(val context: Context) {

    val db = FilterDbHelper(context).writableDatabase

    val parser = Gson()

    fun save(itemFilter: PoeItemFilter): Long? {
        Log.i("PoeItemFilterDao", "Saving filter = ${itemFilter}")
        val values = ContentValues().apply {
            put(FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME, itemFilter.filterName)
            put(FilterContract.FilterEntry.LEAGUE_NAME, itemFilter.league)
        }
        return db?.insert(FilterContract.FilterEntry.TABLE_NAME, null, values)
    }

    fun find(id: Long): PoeItemFilter {
        val selection = "${FilterContract.FilterEntry.ID_NAME} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
                FilterContract.FilterEntry.ID_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
             null
        )
        val poeItemFilterList = arrayListOf<PoeItemFilter>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME))
                val league = getString(getColumnIndexOrThrow(FilterContract.FilterEntry.LEAGUE_NAME))
                val filter = PoeItemFilter(name, league)
                poeItemFilterList.add(filter)
            }
        }
        return poeItemFilterList[0]
    }

}