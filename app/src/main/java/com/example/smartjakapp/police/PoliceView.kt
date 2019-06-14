package com.example.smartjakapp.police

import android.content.Context
import com.example.smartjakapp.model.police.Data

interface PoliceView {
    interface MainView {
        fun getData(response: List<Data>?)
        fun loadingStart()
        fun loadingEnd()
        fun loadingError(error: String?)
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
        fun saveData(data: Data, context: Context?)
        fun addFavorite(data: Data, context: Context?)
        fun selectFavorite(context: Context?, favorited: MutableList<Int>)
        fun selectFavoriteId(data: Data, context: Context?): Boolean
        fun deleteFavorite(data: Data, context: Context?)
    }
}