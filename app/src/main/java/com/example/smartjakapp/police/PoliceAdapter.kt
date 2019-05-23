package com.example.smartjakapp.police

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.police.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceAdapter(private val data: List<Data>) : RecyclerView.Adapter<PoliceBindItem>(), Filterable {

    private var filterData: List<Data> = data

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val input: String = constraint.toString().toLowerCase()
                filterData = if (constraint.isNullOrEmpty()) {
                    data
                } else {
                    val result = mutableListOf<Data>()
                    for (row: Data in data) {
                        val tempName = row.name.toLowerCase()
                        if (tempName.contains(input)) {
                            Log.d("temp name ",row.name)
                            result.add(row)
                        }
                    }
                    result
                }
                val filterResult = FilterResults()
                filterResult.values = filterData
                return filterResult
            }

            //            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    filterData = results.values as List<Data>
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoliceBindItem {
//        return PoliceBindItem(PoliceUI().createView(AnkoContext.create(parent.context, parent, false)))
        return PoliceBindItem(LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false))
    }

    override fun getItemCount(): Int = filterData.size

    override fun onBindViewHolder(holder: PoliceBindItem, position: Int) {
        holder.bindItem(filterData[position])
    }
}

