package com.example.smartjakapp.db

/**
 * Created by Bencoleng on 11/06/2019.
 */
data class Favorite(
    val id: Long,
    val id_item: Int,
    val name: String,
    val phone: Int,
    val addres: String,
    val lat: Double,
    val lng: Double
) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_ITEM: String = "ID_ITEM"
        const val NAME: String = "FAV_NAME"
        const val PHONE: String = "FAV_PHONE"
        const val ADDRESS: String = "FAV_ADDRESS"
        const val LAT: String = "FAV_LAT"
        const val LNG: String = "FAV_LNG"
    }
}