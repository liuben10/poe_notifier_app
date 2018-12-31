package com.example.benja.poebrowser.model

data class PoeRequirementSpec(
        val name: String = "",
        val values: List<List<Any>> = mutableListOf(),
        val type: Int? = null
)
