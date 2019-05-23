package com.example.smartjakapp.hospital

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 22/05/2019.
 */
class HospitalHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nama: TextView = view.findViewById(R.id.jeneng)
    private val jenis: TextView = view.findViewById(R.id.alamat)
    private val gambar: ImageView = view.findViewById(R.id.gambar)
    private val conteiner: RelativeLayout = view.findViewById(R.id.container_item)

    fun bindItem(e: Feature) {
        nama.text = e.properties.namaRsu
        jenis.text = e.properties.jenisRsu
        nama.setTextColor(Color.BLACK)
        jenis.setTextColor(Color.BLACK)
        gambar.setImageResource(R.drawable.hospital)
        conteiner.setBackgroundColor(Color.GREEN)
    }
}