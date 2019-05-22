package com.example.smartjakapp.satpolpp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.satpolpp.Data

/**
 * Created by Bencoleng on 22/05/2019.
 */
class SatpolppHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val address: TextView = view.findViewById(R.id.address)

    fun bindItem(e: Data) {
        name.text = e.nama
        address.text = e.alamat
    }
}