package com.example.smartjakapp.police

import android.view.View
import android.widget.ImageView
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
    private val favorite: ImageView = view.findViewById(R.id.favorite)

    fun bindItem(e: Data, listener: (Data) -> Unit, fav: (Data) -> Unit, favoritedId: List<Int>) {
        address.text = e.address
        name.text = e.name
        image.setImageResource(R.drawable.police)
        itemView.setOnClickListener {
            listener(e)
        }

        var isFavorited = false

        favorite.setImageResource(R.drawable.favorite)

        for (i in 0 until favoritedId.size) {
            if (e.placemarkId == favoritedId[i]) {
                favorite.setImageResource(R.drawable.favorited)
                isFavorited = true
                break
            }
        }

        favorite.setOnClickListener {
            isFavorited = !isFavorited
            if (isFavorited) {
                favorite.setImageResource(R.drawable.favorited)
            } else {
                favorite.setImageResource(R.drawable.favorite)
            }
            fav(e)
        }
    }
}