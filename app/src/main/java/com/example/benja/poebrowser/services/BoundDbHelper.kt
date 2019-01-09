package com.example.benja.poebrowser.services

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.benja.poebrowser.CREATE_FILTER_TABLE
import com.example.benja.poebrowser.DELETE_FILTER_TABLE
import com.example.benja.poebrowser.FilterDbHelper
import com.example.benja.poebrowser.SQLBindings
import com.example.benja.poebrowser.model.Saveable
import kotlin.reflect.KClass

// TODO Ugly that we need an instance. Should just be able to find from the mapping
class BoundDbHelper<T>(context: Context, val singleObject: T) : SQLiteOpenHelper(context, FilterDbHelper.DATABASE_NAME, null, FilterDbHelper.DATABASE_VERSION) where T : Saveable {
    val sqlBindings = SQLBindings.bindings[singleObject.getSaveableName()]!!

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlBindings.create())
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(sqlBindings.dropTable())
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun getExtras(): Map<String, String> {
        return sqlBindings.extraSql()
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FilterDb.db"
        fun instance(context: Context): FilterDbHelper {
            return FilterDbHelper(context)
        }
    }

}