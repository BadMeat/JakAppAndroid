package com.example.smartjakapp.network

import com.example.smartjakapp.model.Police
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetDataService {

    @GET("emergency/pospolda?format=geojson")
    fun getData(
        @Header("Authorization") head: String
    ): Observable<Response<List<Police>>>
}