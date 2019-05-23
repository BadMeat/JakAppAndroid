package com.example.smartjakapp.satpolpp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.satpolpp.Data
import org.jetbrains.anko.imageResource

/**
 * Created by Bencoleng on 22/05/2019.
 */
class SatpolppHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.jeneng)
    private val address: TextView = view.findViewById(R.id.alamat)
    private val images: ImageView = view.findViewById(R.id.gambar)
    private val container: RelativeLayout = view.findViewById(R.id.container_item)

    fun bindItem(e: Data, gambar: Int) {
        name.text = e.nama
        address.text = e.alamat
        name.setTextColor(Color.BLACK)
        address.setTextColor(Color.BLACK)
        images.imageResource = gambar
        container.setBackgroundColor(Color.YELLOW)
    }
}