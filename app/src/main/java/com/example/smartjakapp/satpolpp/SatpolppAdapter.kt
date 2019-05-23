package com.example.smartjakapp.satpolpp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.satpolpp.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
class SatpolppAdapter(private val e: List<Data>) : RecyclerView.Adapter<SatpolppHolder>(), Filterable {

    private var filterData: List<Data> = e

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val input = constraint.toString().toLowerCase()
                filterData = if (constraint.isNullOrEmpty()) {
                    e
                } else {
                    val result: MutableList<Data> = mutableListOf()
                    for (w: Data in e) {
                        val tempName: String = w.nama.toLowerCase()
                        if (tempName.contains(input)) {
                            result.add(w)
                        }
                    }
                    result
                }
                val filterResult = FilterResults()
                filterResult.values = filterData
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    filterData = results.values as List<Data>
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatpolppHolder {
        return SatpolppHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false))
    }

    override fun getItemCount() = filterData.size

    override fun onBindViewHolder(holder: SatpolppHolder, position: Int) {
        holder.bindItem(filterData[position], R.drawable.satpolpp)
    }
}