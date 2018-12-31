package com.example.benja.poebrowser.model

data class PublicStashChanges (
        val next_change_id: String = "",
        val stashes: List<PoeStash> = mutableListOf()
)
