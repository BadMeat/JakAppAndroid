package com.example.smartjakapp.model.hospital

import java.io.Serializable


data class Location(
    val alamat: String,
    val latitude: Double,
    val longitude: Double
) : Serializable