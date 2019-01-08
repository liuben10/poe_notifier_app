package com.example.benja.poebrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class ExplicitModsFilterContainerFragment : Fragment() {

    lateinit var explicitModsFilterContainer: LinearLayout
    lateinit var addExplicitModsButton: Button
    private var explicitMods: MutableList<Int> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.explicit_mods_container_fragment, container,false)
    }

    override fun onStart() {
        super.onStart()
        this.explicitModsFilterContainer = this.activity!!.findViewById(R.id.explicit_mods_fields_container)
        this.addExplicitModsButton = this.activity!!.findViewById(R.id.add_explicit_mods_button)
        this.addExplicitModsButton.setOnClickListener {
            val transaction = this.activity!!.supportFragmentManager.beginTransaction()
                val lastCount = if (this.explicitMods.size == 0) {
                    0
                } else {
                    this.explicitMods.last() + 1
                }
                this.explicitMods.add(lastCount)
                val fragmentTag = "explicit_mod_$lastCount"
                val fragment = ExplicitModFieldFragment()
                fragment.withRowId(Integer(lastCount))
                fragment.withRemoveAction {
                    val transaction = this.activity!!.supportFragmentManager.beginTransaction()
                    transaction.remove(fragment)
                    transaction.commit()
                }
                transaction.add(this.explicitModsFilterContainer.id, fragment, fragmentTag)
                transaction.commit()
        }
    }
}