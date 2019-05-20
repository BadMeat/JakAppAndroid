package com.example.smartjakapp.network

import android.util.Log
import com.example.smartjakapp.model.Police
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val dataService: GetDataService

    companion object {
        private val BASE_URL = " http://api.jakarta.go.id/v1/"
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
            .baseUrl(BASE_URL)
            .build()
        dataService = retrofit.create(GetDataService::class.java)
    }

    fun getListPolice(): Observable<Response<List<Police>>> {
        val head = ByteString.encodeUtf8("Jii+apzvYMt2DWzwEYUvLKlWpRy9vsxOecdmqxnEJCPxu0DaQ1BzcvvUn4HsyQSj").base64()
        return dataService.getData(head)
    }
}