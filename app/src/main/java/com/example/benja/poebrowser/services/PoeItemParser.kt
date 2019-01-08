package com.example.benja.poebrowser.services

import com.example.benja.poebrowser.PoeAppContext
import com.example.poe_app_kt.model.PoeItemFilterContainer

class PoeItemParser {

    companion object {
        fun parseChanges(raw: String): PoeItemFilterContainer {
            val parsed = PoeAppContext.getParser().fromJson<PoeItemFilterContainer>(raw, PoeItemFilterContainer::class.java)!!
            return parsed
        }
    }
}