package com.example.mrapp.Api

import com.example.mrapp.Models.MovieApiResp
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "http://task.auditflo.in/"

interface ApiInterface {
    @GET("{pth}")
    fun getMovies(@Path(value = "pth") pth: String): Call<MovieApiResp>



    object apiService {
        val retrofitInstance: ApiInterface
        init {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            retrofitInstance = retrofit.create(ApiInterface::class.java)
        }
    }
}