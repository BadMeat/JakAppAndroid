package com.example.smartjakapp.hospital

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 22/05/2019.
 */
class HospitalAdapter(
    private val e: List<Feature>,
    private val listener: (Feature) -> Unit,
    private val fav: (Any) -> Unit,
    private val favoritedId: List<Int>
) :
    RecyclerView.Adapter<HospitalHolder>(), Filterable {

    private var filterData: List<Feature> = e

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val input: String = constraint.toString().toLowerCase()
                filterData = if (constraint.isNullOrEmpty()) {
                    e
                } else {
                    val tempResult: MutableList<Feature> = mutableListOf()
                    for (data: Feature in e) {
                        val tempName = data.properties.namaRsu.toLowerCase()
                        if (tempName.contains(input)) {
                            tempResult.add(data)
                        }
                    }
                    tempResult
                }
                val filterResult = FilterResults()
                filterResult.values = filterData
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    filterData = results.values as List<Feature>
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalHolder {
        return HospitalHolder(LayoutInflater.from(parent.context).inflate(R.layout.hospital_item, parent, false))
    }

    override fun getItemCount() = filterData.size

    override fun onBindViewHolder(holder: HospitalHolder, position: Int) {
        holder.bindItem(filterData[position], listener, fav, favoritedId)
    }
}