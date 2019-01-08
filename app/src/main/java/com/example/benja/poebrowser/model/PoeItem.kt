package com.example.benja.poebrowser.model

data class PoeItem(
        val x: Int = 0,
        val y: Int = 0,
        val ilvl: Int = 0,
        val abyssJewel: Boolean? = null,
        val properties: MutableList<PoeItemProp> = mutableListOf(),
        val artfileName: String? = "",
        // Figure out how to map
        val category: MutableMap<String, List<String>>? = null,
        val corrupted: Boolean? = null,
        val craftedMods: MutableList<String>? = mutableListOf(),
        val explicitMods: MutableList<String>? = mutableListOf(),
        val implicitMods: MutableList<String>? = mutableListOf(),
        val enchantMods: MutableList<String>? = mutableListOf(),
        val requirements: MutableList<PoeRequirementSpec>? = mutableListOf(),
        val frameType: Int? = 0,
        var league: String? = "",
        var name: String? = "",
        val note: String? = "",
        val sockets: List<PoeSockets>? = mutableListOf(),
        val inventoryId: String? = "",
        val utilityMods: List<String>? = mutableListOf()
) {

    fun kvString(key: String, value: String?): String {
        return "$key: $value"
    }

    fun printAsList(listOfStuff: List<Any>?): String {
        if (listOfStuff == null) {
            return ""
        }
        val listString = StringBuilder()
        for (element in listOfStuff) {
            listString.append(element.toString()).append("\n")
        }
        return listString.toString()
    }

    fun printCategories(): String {
        val sb = StringBuilder("\n===Categories===\n")
        if (this.category == null) {
            return ""
        } else {
            for(key in this.category.keys) {
                val value = this.category[key]
                sb.append("$key -> $value").append("\n")
            }
        }
        return sb.toString()
    }

    fun printModList(modName: String, modList: List<String>?): String {
        val sb = StringBuilder()
        sb.append("\n---").append(modName).append("---\n")
        if (modList == null) {
            return ""
        }
        for (mod in modList) {
            sb.append(mod).append("\n")
        }
        return sb.toString()
    }

    fun toPrettyString(): String {
        val sb = StringBuilder()
        sb.append("\n===========\n")
        sb.append(kvString("name", name)).append("\n")
        sb.append(kvString("league", league)).append("\n")
        sb.append(printModList("craftedMods", craftedMods)).append("\n")
        sb.append(printModList("explicitMods", explicitMods)).append("\n")
        sb.append(printModList("implicitMods", implicitMods)).append("\n")
        sb.append(printModList("enchantMods", enchantMods)).append("\n")
        sb.append(printModList("utilityMods", utilityMods)).append("\n")
        sb.append(printAsList(properties)).append("\n")
        sb.append(printAsList(sockets)).append("\n")
        sb.append(printCategories()).append("\n")
        sb.append(kvString("note", note)).append("\n")
        return sb.toString()
    }
}
