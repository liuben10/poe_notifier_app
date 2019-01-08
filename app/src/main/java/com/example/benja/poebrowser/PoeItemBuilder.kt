package com.example.benja.poebrowser

import com.example.benja.poebrowser.model.PoeItem


class PoeItemBuilder {

    private lateinit var poeItem: PoeItem

    fun PoeItemBuilder() {
        poeItem = PoeItem()
    }

    fun withItemName(name: String): PoeItemBuilder {
        this.poeItem.name = name
        return this
    }

    fun withLeagueName(league: String): PoeItemBuilder {
        this.poeItem.league = league
        return this
    }

    fun withExplicitMods(explicitMods: List<String>): PoeItemBuilder {
        this.poeItem.explicitMods!!.addAll(explicitMods)
        return this
    }

    fun build(): PoeItem {
        return this.poeItem
    }
}