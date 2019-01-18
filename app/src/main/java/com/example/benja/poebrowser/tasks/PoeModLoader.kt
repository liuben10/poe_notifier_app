package com.example.benja.poebrowser.tasks

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.benja.poebrowser.PoeItemMod
import com.example.benja.poebrowser.model.ModTier
import com.example.benja.poebrowser.model.PoeDefensiveItemClass
import com.example.benja.poebrowser.model.PoeItemClass
import com.example.benja.poebrowser.model.PoeModClass
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class PoeModLoader(val context: Context) {
    val parser: JsonParser = JsonParser()

    val defensiveItemClasses = setOf(
            "Shield",
            "Gloves",
            "Helmet",
            "Armor",
            "Boots"
    )

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun loadPoeMods(): List<PoeItemClass> {
        val rawString = this.loadPoeModsRawString()
        val rawJsonObject = parser.parse(rawString)

        val allItemClasses = arrayListOf<PoeItemClass>()
        for(class_k in rawJsonObject.asJsonObject.keySet()) {

            if (!defensiveItemClasses.contains(class_k)) {
                val poeModClasses = mutableListOf<PoeModClass>()
                val entry =             rawJsonObject.asJsonObject.get(class_k)

                val poeModClassesAsMap = entry.asJsonObject

                addAllModClasses(poeModClassesAsMap, poeModClasses)
                allItemClasses.add(PoeItemClass(class_k, poeModClasses, mutableListOf()))
            } else {
                val class_v = rawJsonObject.asJsonObject.get(class_k).asJsonObject

                val allDefensiveItemClasses = mutableListOf<PoeDefensiveItemClass>()

                addAllDefensiveItemClasses(class_v, allDefensiveItemClasses)
                allItemClasses.add(PoeItemClass(class_k, mutableListOf(), allDefensiveItemClasses))
            }
        }
        return allItemClasses
    }

    private fun addAllDefensiveItemClasses(class_v: JsonObject?, allDefensiveItemClasses: MutableList<PoeDefensiveItemClass>) {
        for (defensive_class_k in class_v!!.keySet()) {
            val defensive_class_v = class_v.get(defensive_class_k).asJsonObject
            val poeModClasses = mutableListOf<PoeModClass>()
            addAllModClasses(defensive_class_v, poeModClasses)

            allDefensiveItemClasses.add(PoeDefensiveItemClass(defensive_class_k, poeModClasses))
        }
    }

    fun addAllModClasses(poeModClassesAsMap: JsonObject, poeModClasses: MutableList<PoeModClass>) {
        for (mod_class_k in poeModClassesAsMap.keySet()) {
            val mod_class_v = poeModClassesAsMap.get(mod_class_k).asJsonObject
            val poeItemMods = mutableListOf<PoeItemMod>()
            addAllRollableMods(mod_class_v, poeItemMods)

            poeModClasses.add(PoeModClass(mod_class_k, poeItemMods))
        }
    }

    fun addAllRollableMods(mod_class_v: JsonObject, poeItemMods: MutableList<PoeItemMod>) {
        for (rollable_mod_k in mod_class_v.keySet()) {

            val rollable_mod_v = mod_class_v.get(rollable_mod_k).asJsonArray
            val rollable_mod_tiers = mutableListOf<ModTier>()

            addAllRollableModTiers(rollable_mod_v, rollable_mod_tiers)
            poeItemMods.add(PoeItemMod(rollable_mod_k, rollable_mod_tiers))
        }
    }

    fun addAllRollableModTiers(rollable_mod_v: JsonArray, rollable_mod_tiers: MutableList<ModTier>) {
        for (modTier in rollable_mod_v) {
            val rawModTierObj = modTier.asJsonObject
            val name = rawModTierObj.get("name").asString
            val min = rawModTierObj.get("min").asInt
            val max = rawModTierObj.get("max").asInt
            val lvl = rawModTierObj.get("lvl").asInt
            rollable_mod_tiers.add(ModTier(
                    name, min, max, lvl
            ))
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun loadPoeModsRawString(): String {
        val modStream = context.assets.open("poe_mods.json")
        val container = ByteArray(modStream.available())
        modStream.read(container)
        modStream.close()
        return String(container)
    }
}