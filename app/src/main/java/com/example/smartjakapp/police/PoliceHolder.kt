package com.example.smartjakapp.police

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.checkExistDb
import com.example.smartjakapp.model.police.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.jeneng)
    private val address: TextView = view.findViewById(R.id.alamat)
    private val image: ImageView = view.findViewById(R.id.gambar)
    private val favorite: ImageView = view.findViewById(R.id.favorite)

    fun bindItem(e: Data, listener: (Data) -> Unit, fav: (Any) -> Unit, favoritedId: List<Int>) {
        address.text = e.address
        name.text = e.name
        image.setImageResource(R.drawable.police)
        itemView.setOnClickListener {
            listener(e)
        }

        favorite.setImageResource(R.drawable.favorite)

        checkExistDb(favoritedId, e.placemarkId, favorite, fav, e)
    }
}