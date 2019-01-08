package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import com.example.benja.poebrowser.FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME
import com.example.benja.poebrowser.FilterContract.FilterEntry.ITEM_NAME
import com.example.benja.poebrowser.FilterContract.FilterEntry.LEAGUE_NAME

class ListFiltersFragment : Fragment() {
    lateinit var filterListContainer: ListView
    lateinit var allFiltersAdapter: SimpleCursorAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        allFiltersAdapter = SimpleCursorAdapter(
                this.activity!!,
                R.layout.filter_list,
                PoeAppContext.getPoeItemFilterDao(this.activity!!).allFiltersCursor(),
                arrayOf(
                        FILTER_NAME_COLUMN_NAME,
                        LEAGUE_NAME,
                        ITEM_NAME
                        ),
                intArrayOf(
                        R.id.filter_name,
                        R.id.league_detail_name,
                        R.id.item_detail_name
                ),
                0)

        return inflater.inflate(R.layout.filter_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        filterListContainer = this.activity!!.findViewById(R.id.item_filter_list)
        filterListContainer.adapter = allFiltersAdapter
    }


}