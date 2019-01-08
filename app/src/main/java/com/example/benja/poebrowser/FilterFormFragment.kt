package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.benja.poebrowser.model.PoeItemFilter

class FilterFormFragment : Fragment() {
    lateinit var filterNameField: EditText
    lateinit var leagueField: EditText
    lateinit var submitButton: Button
    lateinit var explicitModsFilterContainerFragment: ExplicitModsFilterContainerFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.add_filter, container, false)
    }

    override fun onStart() {
        super.onStart()
        filterNameField = this.activity!!.findViewById<EditText>(R.id.filter_name)
        leagueField = this.activity!!.findViewById(R.id.league)
        this.explicitModsFilterContainerFragment = this.childFragmentManager.findFragmentById(R.id.explicit_mods_container) as ExplicitModsFilterContainerFragment
        submitButton = this.activity!!.findViewById(R.id.save_filter_button)
        // Really dumb button action
        submitButton.setOnClickListener {
            view ->
                val filterName = filterNameField.text.toString()
                val leagueName = leagueField.text.toString()
                val filterToSave = PoeItemFilter(filterName, leagueName)
                val explicitModFilters = explicitModsFilterContainerFragment.explicitMods()
                filterToSave.explicitMods.addAll(explicitModFilters)
                val savedId = PoeAppContext.getPoeItemFilterDao(this.activity!!).save(filterToSave)
                Log.i("FilterFormFragment", "Saved filter, id=${savedId}")
        }
    }
}