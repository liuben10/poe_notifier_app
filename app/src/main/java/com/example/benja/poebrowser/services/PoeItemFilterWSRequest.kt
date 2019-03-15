package com.example.benja.poebrowser.services

import com.example.benja.poebrowser.model.PoeItemFilter

data class PoeItemFilterWSRequest(
        val nextChangeId: String,
        val poeItemFilters: List<PoeItemFilter>
)