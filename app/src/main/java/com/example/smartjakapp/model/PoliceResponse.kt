package com.example.smartjakapp.model


data class PoliceResponse(
    val `data`: List<Data>,
    val category: String,
    val count: Int,
    val status: String
)