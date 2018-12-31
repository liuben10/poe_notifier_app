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
) {

    fun kvString(key: String, value: String): String {
        return "$key: $value"
    }

    fun printModList(modName: String, modList: List<String>): String {
        val sb = StringBuilder()
        sb.append("\n---").append(modName).append("---\n")
        for (mod in modList) {
            sb.append(mod).append("\n")
        }
        return sb.toString()
    }

    fun toPrettyString(): String {
        val sb = StringBuilder()
        sb.append("===========")
        sb.append(kvString("name", name)).append("\n")
        sb.append(kvString("league", league)).append("\n")
        sb.append(printModList("craftedMods", craftedMods))
        sb.append(printModList("explicitMods", explicitMods))
        sb.append(printModList("implicitMods", implicitMods))
        sb.append(printModList("enchantMods", enchantMods))
        sb.append(printModList("utilityMods", utilityMods))
        return sb.toString()
    }
}
