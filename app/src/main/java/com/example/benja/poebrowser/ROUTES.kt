package com.example.benja.poebrowser

const val ROUTES_LABEL: String = "routes"
val APP_ROUTES: HashMap<String, APP_STATE> = hashMapOf(
        "/fragments" to APP_STATE.FRAGMENTS_LIST,
        "/filter_form" to APP_STATE.FILTER_FORM,
        "/list_filters" to APP_STATE.LIST_FILTERS
)