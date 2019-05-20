package com.example.smartjakapp.model

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("placemark_id")
    val placemarkId: Int = 0,
    @SerializedName("lat")
    val lat: Double = 0.0
)