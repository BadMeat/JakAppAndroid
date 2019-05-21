package com.example.smartjakapp.view

import com.example.smartjakapp.model.Data

interface PoliceView {
    interface MainView {
        fun getData(response: List<Data>?)
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
    }
}