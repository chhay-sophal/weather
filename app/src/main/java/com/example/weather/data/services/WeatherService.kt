package com.example.weather.data.services

import com.example.weather.data.models.WeatherRoot
import com.example.weather.data.models.SearchLocation
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val Base_Url = "https://api.weatherapi.com/v1/"
private const val API_Key = "11b1d4f80a7d49229c473515242408"

interface WeatherService {

    @GET("forecast.json")
    suspend fun getTodayForecast(
        @Query("key") apiKey: String = API_Key,
        @Query("q") location: String
    ): WeatherRoot

    @GET("forecast.json")
    suspend fun getSevenDayForecast(
        @Query("key") apiKey: String = API_Key,
        @Query("q") location: String,
        @Query("days") days: Int = 7
    ): Call<WeatherRoot>

    @GET("search.json")
    suspend fun search(
        @Query("key") apiKey: String = API_Key,
        @Query("q") location: String
    ): List<SearchLocation>

    companion object {
        private var apiService: WeatherService? = null
        fun getInstance(): WeatherService {
            if (apiService == null) {
                val gson = GsonBuilder().setLenient().create()
                apiService = Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(WeatherService::class.java)
            }
            return apiService!!
        }
    }
}