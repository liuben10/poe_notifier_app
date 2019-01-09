package com.example.benja.poebrowser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.benja.poebrowser.model.PoeItem

class PoeItemListAdapter(context: Context, resourceId: Int, var items: MutableList<PoeItem>) : ArrayAdapter<PoeItem>(context, resourceId, items) {
    val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val parser = PoeAppContext.getParser()

    fun updateData(items: MutableList<PoeItem>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var poeItemFilterView = convertView
        if (convertView == null) {
            poeItemFilterView = inflater.inflate(R.layout.poe_item_short_detail, parent, false)
        }
        val nullCheckedView = checkNotNull(poeItemFilterView)

        val item = getItem(position)
        val itemName = nullCheckedView.findViewById<TextView>(R.id.item_name)
        itemName.text = item.name
        val leagueName = nullCheckedView.findViewById<TextView>(R.id.league)
        leagueName.text = item.league

        val explicitModsView = nullCheckedView.findViewById<TextView>(R.id.explicit_mods)
        explicitModsView.text = parser.toJson(item.explicitMods)

        val implicitModsView = nullCheckedView.findViewById<TextView>(R.id.implicit_mods)
        implicitModsView.text = PoeAppContext.getParser().toJson(item.implicitMods)

        val sellerView = nullCheckedView.findViewById<TextView>(R.id.seller)
        sellerView.text = item.seller

        val noteView = nullCheckedView.findViewById<TextView>(R.id.note)
        noteView.text = item.note
        return nullCheckedView
    }

}