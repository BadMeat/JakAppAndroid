package com.example.smartjakapp.Police

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.model.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceBindItem(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val address: TextView = view.findViewById(R.id.address)

    fun bindItem(e: Data) {
        name.text = e.name
        address.text = e.address
    }
}