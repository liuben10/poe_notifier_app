package com.example.benja.poebrowser.model

data class PoeStash(
        val accountName: String = "",
        val lastCharacterName: String = "",
        val id: String = "",
        val stashType: String = "",
        val items: List<PoeItem> = mutableListOf(),
        val public: Boolean = false
)
