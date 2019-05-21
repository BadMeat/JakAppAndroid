package com.example.smartjakapp.Police

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.model.Data
import org.jetbrains.anko.AnkoContext

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceAdapter(private val data: List<Data>) : RecyclerView.Adapter<PoliceBindItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoliceBindItem {
        return PoliceBindItem(PoliceUI().createView(AnkoContext.create(parent.context, parent, false)))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PoliceBindItem, position: Int) {
        holder.bindItem(data[position])
    }
}

