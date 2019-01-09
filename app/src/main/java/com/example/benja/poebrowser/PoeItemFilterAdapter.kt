package com.example.benja.poebrowser

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.benja.poebrowser.model.PoeItemFilter
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView


class PoeItemFilterAdapter(context: Context?, resource: Int, val values: MutableList<PoeItemFilter>) : ArrayAdapter<PoeItemFilter>(context, resource, values) {
    val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var poeItemFilterView = convertView
        if (convertView == null) {
            poeItemFilterView = inflater.inflate(R.layout.filter_short_detail, parent, false)
        }
        val nullCheckedView = checkNotNull(poeItemFilterView)

        val filter = getItem(position)
        val filterDetailName = nullCheckedView.findViewById<TextView>(R.id.filter_detail_name)
        filterDetailName.text = filter.filterName
        val itemDetailName = nullCheckedView.findViewById<TextView>(R.id.item_name_label)
        itemDetailName.text = filter.name
        val leagueView = nullCheckedView.findViewById<TextView>(R.id.league_detail_name)
        leagueView.text = filter.league
        val explicitModsView = nullCheckedView.findViewById<TextView>(R.id.explicit_mod_row_detail)
        explicitModsView.text = PoeAppContext.getParser().toJson(filter.explicitMods)

        val deleteFilterButton = nullCheckedView.findViewById<Button>(R.id.delete_filter_button)
        deleteFilterButton.setOnClickListener {
            view ->
                PoeAppContext.getPoeItemFilterDao(this.context!!).db.delete(FilterContract.FilterEntry.TABLE_NAME,
                        FilterContract.FilterEntry.FILTER_NAME_COLUMN_NAME + " = " + filter.filterName + " OR " + FilterContract.FilterEntry.ID_NAME + " = " + filter.id,
                        null)
                this.values.removeAt(position)
                this.notifyDataSetChanged()
        }
        return nullCheckedView
    }
}