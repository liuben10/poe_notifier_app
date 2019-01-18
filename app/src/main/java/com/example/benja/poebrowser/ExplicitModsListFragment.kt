package com.example.benja.poebrowser

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.TextView


class ExplicitModsListFragment : ListFragment() {

    lateinit var searchField: EditText

    class ExplicitModsSearchListAdapter(context: Context, layoutId: Int) : ArrayAdapter<PoeItemMod>(context, layoutId) {
        val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val currentMod = getItem(position)
            var poeExplicitModView = convertView
            if (convertView == null) {
                poeExplicitModView = inflater.inflate(R.layout.poe_item_mod_view_element, parent, false)
            }
            val textView = poeExplicitModView!!.findViewById<TextView>(R.id.mod_name_view)
            textView.text = currentMod.name
            return poeExplicitModView!!
        }
}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        this.searchField = this.activity!!.findViewById(R.id.explicit_mods_search_field)
        this.listAdapter = ExplicitModsSearchListAdapter(this.activity!!, R.layout.poe_item_mod_view_element)
        return inflater.inflate(R.layout.explicit_mods_search_layout, container, false)
    }

}