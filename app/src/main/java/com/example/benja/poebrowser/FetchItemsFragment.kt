package com.example.benja.poebrowser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PoeStash
import com.example.benja.poebrowser.services.ITEMS_LABEL
import com.example.benja.poebrowser.tasks.UpdateNotifierTask

class FetchItemsFragment : Fragment() {

    class ItemFilterBroadCastReceiver( val poeItemAdapter: PoeItemListAdapter, val itemsContainer: MutableList<PoeItem>) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val dao = PoeAppContext.getItemsDao(context!!)
            if (ITEMS_DETECTED_SIGNAL.equals(intent!!.action)) {
                itemsContainer.addAll(dao.cursorToList(dao.fetchTop5()!!))
                this.poeItemAdapter.items.addAll(itemsContainer)
                this.poeItemAdapter.updateData(itemsContainer)
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {// Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.item_fetch_fragment, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        bindFetchButton()
    }

    private fun bindFetchButton() {
        val fetchItemsButton = this.activity!!.findViewById<Button>(R.id.fetch_items)
        fetchItemsButton.setOnClickListener {
            view ->
                val notifierTask = UpdateNotifierTask(this.context!!)
                notifierTask.execute()
        }
    }
}