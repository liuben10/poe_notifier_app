package com.example.benja.poebrowser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*


class MainBodyFragment : Fragment() {

    var currentState: APP_STATE? = null

    lateinit var navigationReceiver: BroadcastReceiver
    lateinit var inflater: LayoutInflater
    var container: ViewGroup? = null
    lateinit var layoutView: ConstraintLayout

    class NavStateBroadcastReceiver(val update_state: (APP_STATE) -> Unit) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (NAVIGATION.equals(intent!!.action)) {
                val route = intent.getStringExtra(ROUTES_LABEL)
                update_state(APP_ROUTES[route]!!)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        this.inflater = inflater
        this.container = container

        return inflater.inflate(R.layout.main_body_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        initializeState()
        bindNavigationReceiver()
    }

    fun initializeState() {
        layoutView = this.activity!!.findViewById(R.id.main_body_fragment_layout)
        addToState(APP_STATE.FILTER_FORM)
//        val transaction = this.fragmentManager!!.beginTransaction()
//        transaction.add(newLayoutView.id, APP_STATE.FRAGMENTS_LIST.fragmentInstance())
//        transaction.commit()
    }

    fun bindNavigationReceiver() {
        val intentFilter = IntentFilter(NAVIGATION)
        this.navigationReceiver = NavStateBroadcastReceiver { app_state ->
            this.updateMainView(app_state)
        }
        this.activity!!.baseContext.registerReceiver(navigationReceiver, intentFilter)
    }

    private fun updateMainView(app_state: APP_STATE) {
        if (app_state != this.currentState) {
            navigateToState(app_state)
            this.currentState = app_state
        }
    }

    fun addToState(app_state: APP_STATE) {
        val transaction = this.activity!!.supportFragmentManager.beginTransaction()
        transaction.add(this.layoutView.id, app_state.fragmentInstance())
        //            transaction.addToBackStack("current_state_update_${app_state.name}")
        transaction.commit()
    }

    fun navigateToState(app_state: APP_STATE) {
        val transaction = this.activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(this.layoutView.id, app_state.fragmentInstance())
        //            transaction.addToBackStack("current_state_update_${app_state.name}")
        transaction.commit()
    }
}