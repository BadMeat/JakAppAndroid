package com.example.smartjakapp.hospital

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 22/05/2019.
 */
class HospitalHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nama: TextView = view.findViewById(R.id.name)
    private val jenis: TextView = view.findViewById(R.id.address)

    fun bindItem(e: Feature) {
        nama.text = e.properties.namaRsu
        jenis.text = e.properties.jenisRsu
    }
}