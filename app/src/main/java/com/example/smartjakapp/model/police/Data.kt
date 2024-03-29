package com.example.smartjakapp.model.police


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    val address: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    @SerializedName("placemark_id")
    val placemarkId: Int
) : Serializable