package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benja.poebrowser.model.PoeItemFilter

class ListFiltersFragment : ListFragment() {
    lateinit var allFiltersAdapter: PoeItemFilterAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val itemFilters = mutableListOf<PoeItemFilter>()
        itemFilters.addAll(PoeAppContext.getPoeItemFilterDao(this.context!!).allFiltersList())
        allFiltersAdapter = PoeItemFilterAdapter(this.context, R.layout.filter_short_detail, itemFilters)

        this.listAdapter = allFiltersAdapter

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.filter_list, container, false)
    }
}