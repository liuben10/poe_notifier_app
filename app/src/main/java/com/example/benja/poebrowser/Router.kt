package com.example.benja.poebrowser

import android.content.Intent

class Router {
    companion object {
        fun route(itemId: Int): Intent {
            val action = Intent(NAVIGATION)
            when(itemId) {
                R.id.add_filter_nav_item ->
                    action.putExtra(ROUTES_LABEL, "/filter_form")
                R.id.list_items ->
                    action.putExtra(ROUTES_LABEL, "/fragments")
                R.id.list_filters ->
                    action.putExtra(ROUTES_LABEL, "/list_filters")
            }
            return action
        }
    }
}