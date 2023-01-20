package com.jdacodes.sampleanimelist.network

import com.jdacodes.sampleanimelist.model.Data
import com.jdacodes.sampleanimelist.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe")
        .client(OkHttpClient())
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
