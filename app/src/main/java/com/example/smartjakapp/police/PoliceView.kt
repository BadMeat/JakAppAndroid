package com.example.smartjakapp.police

import com.example.smartjakapp.model.police.Data

interface PoliceView {
    interface MainView {
        fun getData(response: List<Data>?)
        fun loadingStart()
        fun loadingEnd()
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
    }
}