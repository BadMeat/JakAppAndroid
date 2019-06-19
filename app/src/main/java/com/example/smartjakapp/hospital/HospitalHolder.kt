package com.example.smartjakapp.hospital

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.smartjakapp.R
import com.example.smartjakapp.checkExistDb
import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 22/05/2019.
 */
class HospitalHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val address: TextView = view.findViewById(R.id.address)
    private val phone: TextView = view.findViewById(R.id.phone)
    private val website: TextView = view.findViewById(R.id.website)
    private val email: TextView = view.findViewById(R.id.email)
    private val favorite: ImageView = view.findViewById(R.id.favorite)
    private val animation: LottieAnimationView = view.findViewById(R.id.animation)

    fun bindItem(e: Feature, listener: (Feature) -> Unit, fav: (Any) -> Unit, favoriteId: List<Int>) {
        name.text = e.properties.namaRsu
        address.text = e.properties.location.alamat
        var phones = ""
        for (i in 0 until e.properties.telepon.size) {
            phones += if (i < e.properties.telepon.size - 1) {
                "${e.properties.telepon[i]},"
            } else {
                e.properties.telepon[i]
            }
        }
        phone.text = phones
        website.text = e.properties.website?.toString()
        email.text = e.properties.email?.toString()
        itemView.setOnClickListener {
            listener(e)
        }

        favorite.setImageResource(R.drawable.favorite)

        checkExistDb(favoriteId, e.properties.id, favorite, fav, e, animation)
    }
}