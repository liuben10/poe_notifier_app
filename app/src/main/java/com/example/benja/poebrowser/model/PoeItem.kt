package com.example.benja.poebrowser.model

import com.beust.klaxon.JsonObject

data class PoeItem(
        val x: Int,
        val y: Int,
        val ilvl: Int,
        val abyssJewel: Boolean? = null,
        val properties: List<PoeItemProp> = mutableListOf(),
        val artfileName: String = "",
        // Figure out how to map
        val category: MutableMap<String, List<String>>,
        val corrupted: Boolean? = null,
        val craftedMods: List<String> = mutableListOf(),
        val explicitMods: List<String> = mutableListOf(),
        val implicitMods: List<String> = mutableListOf(),
        val enchantMods: List<String> = mutableListOf(),
        val requirements: List<PoeRequirementSpec> = mutableListOf(),
        val frameType: Int,
        val league: String = "",
        val name: String = "",
        val note: String = "",
        val sockets: List<PoeSockets> = mutableListOf(),
        val inventoryId: String = "",
        val utilityMods: List<String> = mutableListOf()
)
