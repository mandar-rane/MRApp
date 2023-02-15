package com.example.mrapp.Utils

import com.example.mrapp.Api.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ApiInterface by lazy{
        Retrofit.Builder().baseUrl("http://task.auditflo.in")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiInterface::class.java)
    }
}