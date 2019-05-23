package com.example.smartjakapp.satpolpp

import com.example.smartjakapp.model.satpolpp.Data

/**
 * Created by Bencoleng on 21/05/2019.
 */
interface SatpolppView {
    interface MainView {
        fun getData(data: List<Data>?)
        fun loadingStart()
        fun loadingEnd()
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
    }
}