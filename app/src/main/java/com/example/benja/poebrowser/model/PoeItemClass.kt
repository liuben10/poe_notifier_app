package com.example.benja.poebrowser.model

import com.example.benja.poebrowser.PoeItemMod

data class PoeItemClass (
        val name: String,
        val rollableMods: List<PoeModClass> = mutableListOf(),
        val rollableDefensiveItemClasses: List<PoeDefensiveItemClass> = mutableListOf()
)