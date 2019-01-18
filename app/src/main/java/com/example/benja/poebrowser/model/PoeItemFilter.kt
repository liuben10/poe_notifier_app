package com.example.benja.poebrowser.model

import com.example.poe_app_kt.model.PoeModStringItemFilter

data class PoeItemFilter (
        val filterName: String? = null,
        val league: String,
        var id: Long? = null,
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
        var name: String? = "",
        var sockets: MutableList<PoeSockets> = mutableListOf()
) : Saveable {
    override fun getSaveableName(): String {
        return this::class.java.simpleName
    }

    companion object { // Don't know how to make all implementers have this object so for now coding in each
        fun getSaveableName(): String = PoeItemFilter::class.java.simpleName
    }
}