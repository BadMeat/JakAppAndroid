package com.example.smartjakapp.model

import com.google.gson.annotations.SerializedName

data class Police(
    @SerializedName("data")
    val data: List<DataItem>?,
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("category")
    val category: String = "",
    @SerializedName("status")
    val status: String = ""
)