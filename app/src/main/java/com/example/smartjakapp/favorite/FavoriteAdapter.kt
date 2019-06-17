package com.example.smartjakapp.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.db.Favorite

/**
 * Created by Bencoleng on 12/06/2019.
 */
class FavoriteAdapter(private val data: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(data[position])
    }

    class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = view.findViewById(R.id.name)
        private val phone: TextView = view.findViewById(R.id.phone)
        private val address: TextView = view.findViewById(R.id.address)

        fun bindItem(fav: Favorite) {
            name.text = fav.name
            phone.text = fav.phone
            address.text = fav.addres
        }
    }
}