package com.example.benja.poebrowser

import android.provider.BaseColumns


const val CREATE_DUMB =
        "CREATE TABLE ${FilterContract.FilterEntry.TABLE_NAME}" +
                "(" +
                "   ${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "   ${FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME}  STRING," +
                "   ${FilterContract.FilterEntry.LEAGUE_NAME}   STRING" +
                 ")"

const val DELETE_DUMB =
        "DROP TABLE IF EXISTS ${FilterContract.FilterEntry.TABLE_NAME}"