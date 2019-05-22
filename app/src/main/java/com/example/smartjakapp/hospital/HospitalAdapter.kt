package com.example.smartjakapp.hospital

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.Police.PoliceUI
import com.example.smartjakapp.model.hospital.Feature
import org.jetbrains.anko.AnkoContext

/**
 * Created by Bencoleng on 22/05/2019.
 */
class HospitalAdapter(private val e: List<Feature>) : RecyclerView.Adapter<HospitalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalHolder {
        val anko = AnkoContext.create(parent.context, parent, false)
        val ui = PoliceUI().createView(anko)
        return HospitalHolder(ui)
    }

    override fun getItemCount() = e.size

    override fun onBindViewHolder(holder: HospitalHolder, position: Int) {
        holder.bindItem(e[position])
    }
}