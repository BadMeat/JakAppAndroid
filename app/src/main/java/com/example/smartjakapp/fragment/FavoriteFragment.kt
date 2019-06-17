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

    private val data: MutableList<Favorite> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorite_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectFavorite()
        val adapter = FavoriteAdapter(data)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()
    }

    private fun selectFavorite() {
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(TYPE_ = {type})",
                    "type" to 1
                )
            val favorite = result.parseList(classParser<Favorite>())
            data.addAll(favorite)
        }
    }
}