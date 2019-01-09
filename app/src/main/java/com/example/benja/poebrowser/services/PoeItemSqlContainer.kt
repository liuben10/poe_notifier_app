package com.example.benja.poebrowser.services

import android.provider.BaseColumns
import com.example.benja.poebrowser.FilterContract
import com.example.benja.poebrowser.PoeItemContract
import com.example.benja.poebrowser.SQLContainer

class PoeItemSqlContainer : SQLContainer {

    override fun create(): String {
        return CREATE_FILTER_TABLE
    }

    override fun dropTable(): String {
        return DROP_TABLE
    }

    override fun extraSql(): Map<String, String> {
        return mutableMapOf()
    }

    companion object {
        val CREATE_FILTER_TABLE =
                "CREATE TABLE IF NOT EXISTS ${PoeItemContract.TABLE_NAME}" +
                        "(" +
                        "   ${BaseColumns._ID} LONG PRIMARY KEY," +
                        "   ${PoeItemContract.PoeItemEntry.ITEM_NAME} STRING," +
                        "   ${PoeItemContract.PoeItemEntry.I_LVL_NAME} INT," +
                        "   ${PoeItemContract.PoeItemEntry.LEAGUE_NAME} STRING," +
                        "   ${PoeItemContract.PoeItemEntry.ARTFILE_NAME} STRING," +
                        "   ${PoeItemContract.PoeItemEntry.ABYSS_JEWEL_NAME} BOOLEAN," +
                        "   ${PoeItemContract.PoeItemEntry.ENCHANT_MODS_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.INVENTORY_ID_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.EXPLICIT_MODS_NAME}   STRING," +
                        "   ${PoeItemContract.PoeItemEntry.UTILITY_MODS_NAME}   STRING," +
                        "   ${PoeItemContract.PoeItemEntry.CRAFTED_MODS_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.REQUIREMENTS_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.IMPLICIT_MODS_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.FRAME_TYPE_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.SOCKETS_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.CATEGORY_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.NOTE}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.PROPERTIES_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.CORRUPTED_NAME}  STRING," +
                        "   ${PoeItemContract.PoeItemEntry.SELLER_NAME} STRING" +
                        ")"
        val DROP_TABLE =
                "DROP TABLE IF EXISTS ${PoeItemContract.TABLE_NAME}"

        val SELECT_5 =
                "SELECT * FROM ${PoeItemContract.TABLE_NAME} LIMIT 5"
        const val FILTER_FOR_COUNT =
                "SELECT 1 FROM ${FilterContract.FilterEntry.TABLE_NAME}"
        const val FIND_ALL_FILTERS =
                "SELECT * FROM ${FilterContract.FilterEntry.TABLE_NAME}"
    }

}