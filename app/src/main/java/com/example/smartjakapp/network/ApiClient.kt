package com.example.smartjakapp.network

import com.example.smartjakapp.BuildConfig
import com.example.smartjakapp.model.PoliceResponse
import com.example.smartjakapp.model.hospital.HospitalResponse
import com.example.smartjakapp.model.satpolpp.SatpolppResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val dataService: GetDataService

    companion object {
        private var apiClient: ApiClient? = null
        val instance: ApiClient
            get() {
                if (apiClient == null) {
                    apiClient = ApiClient()
                }
                return apiClient as ApiClient
            }
    }

    init {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
        dataService = retrofit.create(GetDataService::class.java)
    }

    fun getListPolice(): Observable<Response<PoliceResponse>> {
        return dataService.getData()
    }

    fun getListHospital(): Observable<Response<HospitalResponse>> {
        return dataService.getDataRS()
    }

    fun getListSatpolpp(): Observable<Response<SatpolppResponse>> {
        return dataService.getDataSatpolpp()
    }
}