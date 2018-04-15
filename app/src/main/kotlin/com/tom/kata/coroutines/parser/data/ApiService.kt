package com.tom.kata.coroutines.parser.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * Old school retrofit service.
 */
interface ApiService {
    @GET("/@tom.koptel/espresso-initialtouchmode-can-shoot-you-in-the-leg-85c5f922754")
    fun blogPost(): Deferred<ResponseBody>

    /**
     * Factory object that creates ApiService instance. Originally this is done in DI frameworks. For the sake of
     * simplicity one is not used in project.
     */
    object Factory {
        fun create(okHttpClient: OkHttpClient = OkHttpClient()): ApiService {
            return Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://medium.com/")
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
        }
    }
}