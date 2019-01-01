package com.example.poe_app_kt.model

import com.example.benja.poebrowser.model.PoeItemProp
import com.example.benja.poebrowser.model.PoeRequirementSpec
import com.example.benja.poebrowser.model.PoeSockets

data class PoeItemFilter (
        val id: String,
        val league: String,
        var required: Boolean = false,
        var minIlvl: Int? = -1,
        var maxIlvl: Int? = -1,
        var frameType: Int? = 0,
        var abyssJewel: Boolean? = null,
        var properties: MutableList<PoeItemProp> = mutableListOf(),
        // Figure out how to map
        var category: MutableMap<String, List<String>> = mutableMapOf(),
        var corrupted: Boolean? = null,
        var craftedMods: MutableList<PoeModStringItemFilter> = mutableListOf(),
        var explicitMods: MutableList<PoeModStringItemFilter> = mutableListOf(),
        var implicitMods: MutableList<PoeModStringItemFilter> = mutableListOf(),
        var enchantMods: MutableList<PoeModStringItemFilter> = mutableListOf(),
        var utilityMods: MutableList<PoeModStringItemFilter> = mutableListOf(),
        var requirements: MutableList<PoeRequirementSpec> = mutableListOf(),
        var name: String = "",
        var sockets: MutableList<PoeSockets> = mutableListOf()
)