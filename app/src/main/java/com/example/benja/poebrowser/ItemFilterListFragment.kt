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
import android.widget.TextView
import com.example.benja.poebrowser.services.ITEMS_LABEL

class ItemFilterListFragment : Fragment() {

    lateinit var receiver: BroadcastReceiver

    class ItemFilterBroadCastReceiver(val viewToUpdate: TextView) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ITEMS_DETECTED_SIGNAL.equals(intent!!.action)) {
                val body = intent.getStringExtra(ITEMS_LABEL)
                viewToUpdate.text = body
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {// Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.request_fragment, container, false)
        val contentBody: TextView = view!!.findViewById(R.id.render)
        this.receiver = ItemFilterListFragment.ItemFilterBroadCastReceiver(contentBody)
        val intentFilter = IntentFilter(ITEMS_DETECTED_SIGNAL)
        this.activity!!.baseContext.registerReceiver(receiver, intentFilter)
        return view
    }

    override fun onStop() {
        super.onStop()
        this.activity!!.baseContext.unregisterReceiver(this.receiver)
    }
}