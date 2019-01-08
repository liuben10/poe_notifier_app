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
import android.widget.TextView
import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PoeStash
import com.example.benja.poebrowser.services.ITEMS_LABEL
import com.example.benja.poebrowser.tasks.UpdateNotifierTask

class PoeItemListFragment : Fragment() {

    lateinit var receiver: BroadcastReceiver

    class ItemFilterBroadCastReceiver(val viewToUpdate: ListView) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ITEMS_DETECTED_SIGNAL.equals(intent!!.action)) {
                val body = intent.getStringExtra(ITEMS_LABEL)
                val poeStash = PoeAppContext.getParser().fromJson<PoeStash>(body, PoeStash::class.java)
                val adapter = ArrayAdapter<String>(
                        context,
                        R.layout.filter_short_detail,
                        poeStash.items.map { item -> item.toString() }
                )
                viewToUpdate.adapter = adapter
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {// Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.request_fragment, container, false)
        val contentBody: ListView = view!!.findViewById(R.id.render)
        this.receiver = PoeItemListFragment.ItemFilterBroadCastReceiver(contentBody)
        val intentFilter = IntentFilter(ITEMS_DETECTED_SIGNAL)
        this.activity!!.baseContext.registerReceiver(receiver, intentFilter)
        return view
    }

    override fun onStart() {
        super.onStart()
        bindFetchButton()
    }

    override fun onStop() {
        super.onStop()
        this.activity!!.baseContext.unregisterReceiver(this.receiver)
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