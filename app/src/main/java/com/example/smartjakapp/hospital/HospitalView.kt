package com.example.smartjakapp.hospital

import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 21/05/2019.
 */
interface HospitalView {
    interface MainView {
        fun getData(e : List<Feature>?)
    }

    interface PresenterView {
        fun loadData()
        fun onDestroy()
    }
}