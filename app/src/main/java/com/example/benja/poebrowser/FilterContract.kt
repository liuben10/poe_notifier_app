package com.example.benja.poebrowser

import com.example.benja.poebrowser.model.PoeItemProp
import com.example.benja.poebrowser.model.PoeRequirementSpec
import com.example.benja.poebrowser.model.PoeSockets
import com.example.poe_app_kt.model.PoeModStringItemFilter

object FilterContract {
    object FilterEntry {
        const val ID_NAME = "id"
        const val FILTER_NAME_COLUMN_NAME = "filter_name"
        const val LEAGUE_NAME = "league"
        const val REQUIRED_NAME = "required"
        const val MIN_I_LVL_NAME = "min_i_lvl"
        const val MAX_I_LVL_NAME = "max_i_lvl"
        const val FRAME_TYPE_NAME = "frame_type"
        const val ABYSS_JEWEL_NAME = "abyss_jewel"
        const val PROPERTY_NAME = "properties"
        const val CATEGORY_NAME = "category"
        const val CORRUPTED_NAME = "corrupted"
        const val CRAFTED_MODS_NAME = "crafted_mods"
        const val EXPLICIT_MODS_NAME = "explicit_mods"
        const val IMPLICIT_MODS_NAME = "implicit_mods"
        const val ENCHANT_MODS_NAME = "ecnhant_mods"
        const val UTILITY_MODS_NAME = "utility_mods"
        const val REQUIREMENTS_NAME = "requirements"
        const val NAME_COLUMN_NAME = "name"
        const val SOCKETS_NAME = "sockets"
        const val TABLE_NAME = "poe_filter"
    }
}