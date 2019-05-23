package com.example.smartjakapp.network

import com.example.smartjakapp.BuildConfig
import com.example.smartjakapp.model.hospital.HospitalResponse
import com.example.smartjakapp.model.police.PoliceResponse
import com.example.smartjakapp.model.satpolpp.SatpolppResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetDataService {

    @GET("emergency/pospolda")
    fun getData(
        @Header("Authorization") head: String = BuildConfig.API_KEY,
        @Query("format") format: String = "geojson"
    ): Observable<Response<PoliceResponse>>

    @GET("rumahsakitumum")
    fun getDataRS(
        @Header("Authorization") head: String = BuildConfig.API_KEY,
        @Query("format") format: String = "geojson"
    ): Observable<Response<HospitalResponse>>

    @GET("emergency/petugassatpolpp")
    fun getDataSatpolpp(
        @Header("Authorization") head: String = BuildConfig.API_KEY,
        @Query("format") format: String = "geojson"
    ): Observable<Response<SatpolppResponse>>
}