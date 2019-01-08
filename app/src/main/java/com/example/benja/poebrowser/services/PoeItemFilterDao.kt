package com.example.benja.poebrowser.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.benja.poebrowser.*
import com.example.benja.poebrowser.model.PoeItemFilter
import com.example.poe_app_kt.model.PoeModStringItemFilter

class PoeItemFilterDao(val context: Context) {

    val helper = FilterDbHelper(context)
    val db = helper.writableDatabase
    val MAX_COUNT = 5

    init {
        this.dropTable() // TODO REMOVE FROM TEST
        helper.onCreate(db)
    }

    fun save(itemFilter: PoeItemFilter): Long? {
        if (this.count() >= MAX_COUNT) {
            Log.e("PoeItemFilterDao", "Error, at the maximum number ($MAX_COUNT) filters")
            return -1
        } else {
            Log.i("PoeItemFilterDao", "Saving filter = ${itemFilter}")
            val values = ContentValues().apply {
                put(FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME, itemFilter.filterName)
                put(FilterContract.FilterEntry.LEAGUE_NAME, itemFilter.league)
                put(FilterContract.FilterEntry.EXPLICIT_MODS_NAME, PoeAppContext.getParser().toJson(itemFilter.explicitMods))
            }
            return db?.insert(FilterContract.FilterEntry.TABLE_NAME, null, values)
        }
    }

    fun allFiltersCursor(): Cursor {
        return db.query(
                FilterContract.FilterEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        )
    }

    fun allFiltersList(): List<PoeItemFilter> {
        val cursor = this.allFiltersCursor()
        val items = mutableListOf<PoeItemFilter>()
        with(cursor) {
            while (moveToNext()) {
                items.add(fromCursor(this))
            }
        }
        return items
    }

    fun count(): Int {
       return this.allFiltersList().size
    }

    fun fromCursor(cursor: Cursor): PoeItemFilter {
        val filterName = cursor.getString(cursor.getColumnIndexOrThrow(FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME))
        val league = cursor.getString(cursor.getColumnIndexOrThrow(FilterContract.FilterEntry.LEAGUE_NAME))
        val explicitMods = cursor.getString(cursor.getColumnIndexOrThrow(FilterContract.FilterEntry.EXPLICIT_MODS_NAME))
        val explicitModsObj = PoeAppContext.getParser().fromJson<MutableList<PoeModStringItemFilter>>(explicitMods, MutableList::class.java)
        val filter = PoeItemFilter(filterName, league)
        filter.explicitMods = explicitModsObj
        return filter
    }

    fun query(itemFilter: PoeItemFilter): PoeItemFilter {
        throw UnsupportedOperationException("TODO Implement the query operation")
    }

    fun dropTable() {
        this.db.execSQL(DELETE_FILTER_TABLE)
    }

    fun get(id: Long): PoeItemFilter {
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