package com.example.smartjakapp.satpolpp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.smartjakapp.R
import com.example.smartjakapp.checkExistDb
import com.example.smartjakapp.model.satpolpp.Data

/**
 * Created by Bencoleng on 22/05/2019.
 */
class SatpolppHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val address: TextView = view.findViewById(R.id.address)
    private val phone: TextView = view.findViewById(R.id.phone)
    private val position: TextView = view.findViewById(R.id.position)
    private val favoriteImage: ImageView = view.findViewById(R.id.favorite)
    private val animation: LottieAnimationView = view.findViewById(R.id.animation)

    fun bindItem(e: Data, listener: (Data) -> Unit, fav: (Any) -> Unit, favoritedId: List<Int>) {
        name.text = e.nama
        address.text = e.alamat
        phone.text = e.phone
        position.text = e.jabatan
        itemView.setOnClickListener {
            listener(e)
        }
        checkExistDb(favoritedId, e.userid, favoriteImage, fav, e, animation)
    }
}