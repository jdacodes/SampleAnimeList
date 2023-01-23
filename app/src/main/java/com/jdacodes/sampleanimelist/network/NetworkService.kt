package com.jdacodes.sampleanimelist.network

import com.jdacodes.sampleanimelist.BuildConfig
import com.jdacodes.sampleanimelist.model.Data
import com.jdacodes.sampleanimelist.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkService {
    val BASE_URL = "https://api.jikan.moe"

    val logging = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            this.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    val client = OkHttpClient.Builder().addInterceptor(logging)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val animeService = retrofit.create(AnimeService::class.java)

    suspend fun allAnimes(): List<Data> = withContext(Dispatchers.Default) {
        val result = animeService.getAllAnimes(null).data
        result
    }
}

interface AnimeService {
    @GET("/v4/seasons/now")
    suspend fun getAllAnimes(@Query("page") page: Int?): NetworkResponse

}
