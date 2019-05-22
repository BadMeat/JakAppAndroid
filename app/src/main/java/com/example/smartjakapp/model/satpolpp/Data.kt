package com.example.smartjakapp.model.satpolpp


import com.google.gson.annotations.SerializedName

data class Data(
    val alamat: String,
    val jabatan: String,
    val lat: Any,
    val lng: Any,
    @SerializedName("login_terakhir")
    val loginTerakhir: String,
    val nama: String,
    val phone: String,
    val userid: Int,
    val wilayah: String
)