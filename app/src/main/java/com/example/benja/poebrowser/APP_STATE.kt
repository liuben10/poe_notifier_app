package com.example.benja.poebrowser

import android.support.v4.app.Fragment


enum class APP_STATE(val fragmentInstance: () -> Fragment){
    FRAGMENTS_LIST({FetchItemsFragment()}),
    FILTER_FORM({FilterFormFragment()}),
    LIST_FILTERS({ListFiltersFragment()})
}
