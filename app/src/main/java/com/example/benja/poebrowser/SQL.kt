package com.example.benja.poebrowser

import android.provider.BaseColumns


const val CREATE_FILTER_TABLE =
        "CREATE TABLE ${FilterContract.FilterEntry.TABLE_NAME}" +
                "(" +
                "   ${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "   ${FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME}  STRING," +
                "   ${FilterContract.FilterEntry.LEAGUE_NAME}   STRING" +
                "   ${FilterContract.FilterEntry.EXPLICIT_MODS_NAME}  STRING" +
                 ")"

const val DELETE_FILTER_TABLE =
        "DROP TABLE IF EXISTS ${FilterContract.FilterEntry.TABLE_NAME}"

const val FILTER_FOR_COUNT =
        "SELECT 1 FROM ${FilterContract.FilterEntry.TABLE_NAME}"

const val FIND_ALL_FILTERS =
        "SELECT * FROM ${FilterContract.FilterEntry.TABLE_NAME}"

