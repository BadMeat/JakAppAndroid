package com.example.smartjakapp.satpolpp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.Police.PoliceUI
import com.example.smartjakapp.model.satpolpp.Data
import org.jetbrains.anko.AnkoContext

/**
 * Created by Bencoleng on 21/05/2019.
 */
class SatpolppAdapter(private val e: List<Data>) : RecyclerView.Adapter<SatpolppHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatpolppHolder {
        val ankoContext = AnkoContext.Companion.create(parent.context, parent, false)
        val police = PoliceUI().createView(ankoContext)
        return SatpolppHolder(police)
    }

    override fun getItemCount() = e.size

    override fun onBindViewHolder(holder: SatpolppHolder, position: Int) {
        holder.bindItem(e[position])
    }
}