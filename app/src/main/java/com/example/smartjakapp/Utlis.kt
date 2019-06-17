package com.example.smartjakapp

import android.view.View
import android.widget.ImageView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun checkExistDb(favoritedId: List<Int>, id: Int, favorite: ImageView, fav: (Any) -> Unit, unit: Any) {

    var isFavorited = false

    for (i in 0 until favoritedId.size) {
        if (id == favoritedId[i]) {
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
        fav(unit)
    }
}