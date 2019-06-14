package com.example.smartjakapp.satpolpp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.satpolpp.Data
import org.jetbrains.anko.imageResource

/**
 * Created by Bencoleng on 22/05/2019.
 */
class SatpolppHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val address: TextView = view.findViewById(R.id.address)
    private val images: ImageView = view.findViewById(R.id.logo)
    private val phone: TextView = view.findViewById(R.id.phone)
    private val position: TextView = view.findViewById(R.id.position)

    fun bindItem(e: Data, gambar: Int, listener: (Data) -> Unit) {
        name.text = e.nama
        address.text = e.alamat
//        images.imageResource = gambar
        phone.text = e.phone
        position.text = e.jabatan
        itemView.setOnClickListener {
            listener(e)
        }
    }
}