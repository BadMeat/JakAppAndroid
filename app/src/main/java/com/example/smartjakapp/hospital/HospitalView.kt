package com.example.smartjakapp.hospital

import com.example.smartjakapp.model.hospital.Feature

/**
 * Created by Bencoleng on 21/05/2019.
 */
interface HospitalView {
    interface MainView {
        fun getData(e : List<Feature>?)
        fun loadingStart()
        fun loadingEnd()
        fun loadingError(error : String?)
    }

    interface PresenterView {
        fun loadData(favorited: MutableList<Int>)
        fun onDestroy()
        fun saveData(data: Feature)
    }
}