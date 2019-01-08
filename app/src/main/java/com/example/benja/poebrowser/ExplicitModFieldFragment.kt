package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import java.util.*

class ExplicitModFieldFragment : Fragment() {

    lateinit var removeButton: Button
    lateinit var rowId: Integer
    var removeAction: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.mod_field_view, container, false)
    }

    fun withRemoveAction(action: () -> Unit): ExplicitModFieldFragment {
        this.removeAction = action
        return this
    }

    fun withRowId(rowId: Integer) {
        this.rowId = rowId
    }

    override fun onStart() {
        super.onStart()
        this.removeButton = this.activity!!.findViewById(R.id.remove_explicit_mods)
        this.removeButton.id = rowId.toInt()
        this.removeButton.setOnClickListener {
            this.removeAction!!()
        }
    }
}