package com.example.smartjakapp.model.hospital

import java.io.Serializable


data class Geometry(
    val coordinates: List<Double>,
    val type: String
) : Serializable