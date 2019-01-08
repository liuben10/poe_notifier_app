package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.poe_app_kt.model.PoeModStringItemFilter
import java.lang.Integer.parseInt
import java.util.*

class ExplicitModFieldFragment : Fragment() {

    lateinit var removeButton: Button
    lateinit var threshold: EditText
    lateinit var filter: EditText
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
        this.filter = this.activity!!.findViewById(R.id.mod_body)
        this.threshold = this.activity!!.findViewById(R.id.threshold)
        this.filter.id = ("filter" + rowId.toString()).hashCode()
        this.threshold.id = ("threshold" + rowId.toString()).hashCode()
        this.removeButton.id = ("removeButton" + rowId.toInt()).hashCode()
        this.removeButton.setOnClickListener {
            this.removeAction!!()
        }
    }

    fun parseThreshold(threshold: String): Int {
        val thresholdText = threshold
        if (thresholdText.isEmpty()) {
            return 0
        }
        return parseInt(thresholdText)
    }

    fun toPoeItemModFilter(): PoeModStringItemFilter? {
        if (this.filter.text == null) {
            return null
        }
        val threshold = parseThreshold(this.threshold.text.toString())
        return PoeModStringItemFilter(
                this.filter.text.toString(),
                threshold
        )
    }
}