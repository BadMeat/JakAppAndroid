package com.example.smartjakapp.view

import com.example.smartjakapp.model.Police
import retrofit2.Response

interface PoliceView {
    interface MainView {
        //        fun getData(response: List<DataItem>?)
        fun getData(response: Response<List<Police>>)
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
    }
}