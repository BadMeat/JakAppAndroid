package com.example.smartjakapp.model.satpolpp


import com.google.gson.annotations.SerializedName

data class SatpolppResponse(
    val `data`: List<Data>,
    val count: Int,
    @SerializedName("Dinas")
    val dinas: String,
    val status: String
)