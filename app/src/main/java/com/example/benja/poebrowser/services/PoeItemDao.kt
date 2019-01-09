package com.example.benja.poebrowser.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.PoeItemContract
import com.example.benja.poebrowser.model.PoeItem
import java.util.*

class PoeItemDao(context: Context) {
    val dbHelper = BoundDbHelper(context, PoeItem())
    var db = dbHelper.writableDatabase
    val parser = PoeAppContext.getParser()

    init {
//        dbHelper.onUpgrade(db,0, 1) // TODO remove these calls
    }

    fun save(item: PoeItem): Long? {
        Log.i("PoeItemDao", "Saving item = ${item}")
        val values = ContentValues().apply {
            put(PoeItemContract.PoeItemEntry.ID_NAME, UUID.randomUUID().mostSignificantBits) // TODO fix to use typed IDs
            put(PoeItemContract.PoeItemEntry.ABYSS_JEWEL_NAME, item.abyssJewel)
            put(PoeItemContract.PoeItemEntry.ARTFILE_NAME, item.artfileName)
            put(PoeItemContract.PoeItemEntry.CATEGORY_NAME, parser.toJson(item.category))
            put(PoeItemContract.PoeItemEntry.ENCHANT_MODS_NAME, parser.toJson(item.enchantMods))
            put(PoeItemContract.PoeItemEntry.EXPLICIT_MODS_NAME, parser.toJson(item.explicitMods))
            put(PoeItemContract.PoeItemEntry.IMPLICIT_MODS_NAME, parser.toJson(item.implicitMods))
            put(PoeItemContract.PoeItemEntry.UTILITY_MODS_NAME, parser.toJson(item.utilityMods))
            put(PoeItemContract.PoeItemEntry.CRAFTED_MODS_NAME, parser.toJson(item.craftedMods))
            put(PoeItemContract.PoeItemEntry.CORRUPTED_NAME, item.corrupted)
            put(PoeItemContract.PoeItemEntry.ITEM_NAME, item.name)
            put(PoeItemContract.PoeItemEntry.I_LVL_NAME, item.ilvl)
            put(PoeItemContract.PoeItemEntry.SOCKETS_NAME, parser.toJson(item.sockets))
            put(PoeItemContract.PoeItemEntry.LEAGUE_NAME, item.league)
            put(PoeItemContract.PoeItemEntry.REQUIREMENTS_NAME, parser.toJson(item.requirements))
            put(PoeItemContract.PoeItemEntry.INVENTORY_ID_NAME, parser.toJson(item.inventoryId))
            put(PoeItemContract.PoeItemEntry.NOTE, item.note)
            put(PoeItemContract.PoeItemEntry.SELLER_NAME, item.seller)
        }
        return db?.insert(PoeItemContract.TABLE_NAME, null, values)
    }

    fun saveAll(items: List<PoeItem>) {
        items.forEach { item -> save(item) }
    }

    fun fetchTop5(): Cursor? {
        return db?.query(PoeItemContract.TABLE_NAME, null, null, null, null, null, null, "5")
    }

    fun cursorToList(cursor: Cursor): List<PoeItem> {
        val resultContainer = mutableListOf<PoeItem>()
        with(cursor) {
            while (cursor.moveToNext()) {
                resultContainer.add(fromCursor(this))
            }
        }
        return resultContainer
    }

    fun fromCursor(cursor: Cursor): PoeItem {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.ID_NAME))
        val itemName = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.ITEM_NAME))
        val league = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.LEAGUE_NAME))

        val implicitMods = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.IMPLICIT_MODS_NAME))
        val implicitModsExtracted = PoeAppContext.getParser().fromJson<MutableList<String>>(implicitMods, MutableList::class.java)

        val explicitMods = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.EXPLICIT_MODS_NAME))
        val explicitModsExtracted = PoeAppContext.getParser().fromJson<MutableList<String>>(explicitMods, MutableList::class.java)

        val seller = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.SELLER_NAME))
        val note = cursor.getString(cursor.getColumnIndexOrThrow(PoeItemContract.PoeItemEntry.NOTE))

        val item = PoeItem()
        item.name = itemName
        item.league = league
        item.implicitMods = implicitModsExtracted
        item.explicitMods = explicitModsExtracted
        item.seller = seller
        item.note = note
        item.id = id
        return item
    }
}