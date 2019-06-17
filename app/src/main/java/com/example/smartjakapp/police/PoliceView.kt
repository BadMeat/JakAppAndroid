package com.example.smartjakapp.police

import com.example.smartjakapp.model.police.Data

interface PoliceView {
    interface MainView {
        fun getData(response: List<Data>?)
        fun loadingStart()
        fun loadingEnd()
        fun loadingError(error: String?)
    }

    interface PresenterView {
        fun loadData(favorited: MutableList<Int>)
        fun onDestroy()
        fun saveData(data: Data)
    }
}