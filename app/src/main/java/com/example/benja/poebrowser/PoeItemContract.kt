package com.example.benja.poebrowser

import android.provider.BaseColumns
import com.example.benja.poebrowser.model.PoeItemProp
import com.example.benja.poebrowser.model.PoeRequirementSpec
import com.example.benja.poebrowser.model.PoeSockets

object PoeItemContract {
    object PoeItemEntry {
        val ID_NAME = BaseColumns._ID
        val X_POS_NAME = "x_pos"
        val Y_POS_NAME = "y_pos"
        val I_LVL_NAME = "i_lvl"
        val ABYSS_JEWEL_NAME = "abyss_jewel"
        val PROPERTIES_NAME = "properties"
        val ARTFILE_NAME = "artfile"
        // Figure out how to map
        val CATEGORY_NAME = "category"
        val CORRUPTED_NAME = "corrupted"
        val CRAFTED_MODS_NAME = "crafted_mods"
        val EXPLICIT_MODS_NAME = "explicit_mods"
        val IMPLICIT_MODS_NAME = "implicit_mods"
        val ENCHANT_MODS_NAME = "enchant_mods"
        val REQUIREMENTS_NAME = "requirements"
        val FRAME_TYPE_NAME = "frame_type"
        val LEAGUE_NAME = "league"
        val ITEM_NAME = "item_name"
        val NOTE = "note"
        val SOCKETS_NAME = "sockets"
        val INVENTORY_ID_NAME = "inventory_id"
        val UTILITY_MODS_NAME = "utility_mods"
        val SELLER_NAME = "seller"
    }
    val TABLE_NAME = "poe_item"
}