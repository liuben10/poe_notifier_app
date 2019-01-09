package com.example.benja.poebrowser

import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.services.PoeItemDao

class PoeItemListViewFragment : ListFragment() {

    lateinit var receiver: FetchItemsFragment.ItemFilterBroadCastReceiver
    val fetchedItems = mutableListOf<PoeItem>()
    lateinit var poeItemAdapter: PoeItemListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dao: PoeItemDao = PoeAppContext.getItemsDao(this.context!!)
        val intentFilter = IntentFilter(ITEMS_DETECTED_SIGNAL)
        val top5List = dao.cursorToList(dao.fetchTop5()!!)
        val mutableTop5List = mutableListOf<PoeItem>()
        mutableTop5List.addAll(top5List)
        this.poeItemAdapter = PoeItemListAdapter(this.context!!, R.layout.poe_item_short_detail, mutableTop5List)
        this.receiver = FetchItemsFragment.ItemFilterBroadCastReceiver(this.poeItemAdapter, fetchedItems)
        this.activity!!.baseContext.registerReceiver(receiver, intentFilter)
        this.listAdapter = poeItemAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fetched_items_list_view, container, false)
    }

    override fun onStop() {
        super.onStop()
        this.activity!!.baseContext.unregisterReceiver(this.receiver)
    }
}