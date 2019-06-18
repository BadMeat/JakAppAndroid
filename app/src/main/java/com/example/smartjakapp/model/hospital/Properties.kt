package com.example.smartjakapp.model.hospital


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Properties(
    val email: Any?,
    val faximile: List<String>,
    val id: Int,
    @SerializedName("jenis_rsu")
    val jenisRsu: String,
    @SerializedName("kode_kecamatan")
    val kodeKecamatan: Int,
    @SerializedName("kode_kelurahan")
    val kodeKelurahan: Long,
    @SerializedName("kode_kota")
    val kodeKota: Int,
    @SerializedName("kode_pos")
    val kodePos: Int,
    val location: Location,
    @SerializedName("nama_rsu")
    val namaRsu: String,
    val telepon: List<String>,
    val website: Any?
) : Serializable