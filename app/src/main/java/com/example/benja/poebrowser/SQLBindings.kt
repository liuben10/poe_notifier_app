package com.example.benja.poebrowser

import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PoeItemFilter
import com.example.benja.poebrowser.services.PoeItemFilterSqlContainer
import com.example.benja.poebrowser.services.PoeItemSqlContainer
import kotlin.reflect.KClass


// TODO type safety
object SQLBindings {
    val bindings: Map<String, SQLContainer> =
            hashMapOf(
                    PoeItem.getSaveableName() to PoeItemSqlContainer(),
                    PoeItemFilter.getSaveableName() to PoeItemFilterSqlContainer()
            )
}
