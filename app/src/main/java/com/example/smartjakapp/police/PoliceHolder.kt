package com.example.smartjakapp.police

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.police.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.jeneng)
    private val address: TextView = view.findViewById(R.id.alamat)
    private val image: ImageView = view.findViewById(R.id.gambar)
    private val container: RelativeLayout = view.findViewById(R.id.container_item)

    fun bindItem(e: Data, listener: (Data) -> Unit) {
        address.text = e.address
        name.text = e.name
        image.setImageResource(R.drawable.police)
        container.setBackgroundColor(Color.parseColor("#7A9998"))
        itemView.setOnClickListener {
            listener(e)
        }
    }
}