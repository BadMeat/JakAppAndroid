package com.example.smartjakapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartjakapp.R
import com.example.smartjakapp.db.Favorite
import com.example.smartjakapp.db.database
import com.example.smartjakapp.favorite.FavoriteAdapter
import kotlinx.android.synthetic.main.favorite_layout.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * Created by Bencoleng on 12/06/2019.
 */
class FavoriteFragment : Fragment() {

    private val dataPolice: MutableList<Favorite> = mutableListOf()
    private val dataSatpolpp: MutableList<Favorite> = mutableListOf()
    private val dataHospital: MutableList<Favorite> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorite_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        selectFavorite(1, dataPolice)
        selectFavorite(2, dataSatpolpp)
        selectFavorite(3, dataHospital)

        val adapterPolice = FavoriteAdapter(dataPolice)
        val adapterSatpolpp = FavoriteAdapter(dataSatpolpp)
        val adapterHospital = FavoriteAdapter(dataHospital)

        if (dataPolice.isNullOrEmpty()) {
            dataPolice.add(0, Favorite(0, 0, " - ", " - ", " - ", 12.0, 12.0, 1))
        }
        if (dataSatpolpp.isNullOrEmpty()) {
            dataSatpolpp.add(0, Favorite(0, 0, " - ", " - ", " - ", 12.0, 12.0, 1))
        }
        if (dataHospital.isNullOrEmpty()) {
            dataHospital.add(0, Favorite(0, 0, " - ", " - ", " - ", 12.0, 12.0, 1))
        }

        /**
         * Police
         */
        recycler.adapter = adapterPolice
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterPolice.notifyDataSetChanged()

        /**
         * Satpolpp
         */
        recycler_satpolpp.adapter = adapterSatpolpp
        recycler_satpolpp.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterSatpolpp.notifyDataSetChanged()

        /**
         * Hospital
         */
        recycler_hospital.adapter = adapterHospital
        recycler_hospital.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterHospital.notifyDataSetChanged()
    }

    private fun selectFavorite(type: Int, data: MutableList<Favorite>) {
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(TYPE_ = {type})",
                    "type" to type
                )
            val favorite = result.parseList(classParser<Favorite>())
            data.addAll(favorite)
        }
    }
}