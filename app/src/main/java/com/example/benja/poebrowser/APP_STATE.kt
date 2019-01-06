package com.example.benja.poebrowser

import android.support.v4.app.Fragment
import kotlin.reflect.KClass


enum class APP_STATE(val fragmentInstance: () -> Fragment){
    FRAGMENTS_LIST({ItemFilterListFragment()}),
    FILTER_FORM({FilterFormFragment()})
}
