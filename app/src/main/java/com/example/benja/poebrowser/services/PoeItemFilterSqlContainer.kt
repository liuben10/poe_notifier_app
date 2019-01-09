package com.example.benja.poebrowser.services

import android.provider.BaseColumns
import com.example.benja.poebrowser.FilterContract
import com.example.benja.poebrowser.SQLContainer

class PoeItemFilterSqlContainer : SQLContainer {

    override fun create(): String {
        return CREATE_FILTER_TABLE
    }

    override fun dropTable(): String {
        return DELETE_FILTER_TABLE
    }

    override fun extraSql(): Map<String, String> {
        return hashMapOf(
                "FILTER_FOR_COUNT" to FILTER_FOR_COUNT,
                "FIND_ALL_FILTERS" to FIND_ALL_FILTERS
        )
    }

    companion object {
        const val DELETE_FILTER_TABLE =
                "DROP TABLE IF EXISTS ${FilterContract.FilterEntry.TABLE_NAME}"
        const val FILTER_FOR_COUNT =
                "SELECT 1 FROM ${FilterContract.FilterEntry.TABLE_NAME}"
        const val FIND_ALL_FILTERS =
                "SELECT * FROM ${FilterContract.FilterEntry.TABLE_NAME}"
        const val CREATE_FILTER_TABLE =
                "CREATE TABLE IF NOT EXISTS ${FilterContract.FilterEntry.TABLE_NAME}" +
                        "(" +
                        "   ${BaseColumns._ID} LONG PRIMARY KEY," +
                        "   ${FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME}," +
                        "   ${FilterContract.FilterEntry.ITEM_NAME}  STRING," +
                        "   ${FilterContract.FilterEntry.LEAGUE_NAME}   STRING," +
                        "   ${FilterContract.FilterEntry.EXPLICIT_MODS_NAME}  STRING" +
                        ")"
    }

}