package com.example.smartjakapp.satpolpp

import android.content.Context
import com.example.smartjakapp.model.satpolpp.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
interface SatpolppView {
    interface MainView {
        fun getData(data: List<Data>?)
        fun loadingStart()
        fun loadingEnd()
        fun loadingError(error : String?)
    }

    interface PresenterView {
        fun loadData(favorited: MutableList<Int>)
        fun onDestroy()
        fun saveData(data : Data)
    }
}