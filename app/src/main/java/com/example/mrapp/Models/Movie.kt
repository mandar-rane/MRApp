package com.example.mrapp.Models

import com.google.gson.annotations.SerializedName

data class Movie(
    val Title: String = "",
    val Year: String = "",
    val Summary: String = "",
    @SerializedName("Short Summary") val Short_Summary: String = "",
    val Genres: String = "",
    val IMDBID: String = "",
    val Runtime: String = "",
    @SerializedName("YouTube Trailer") val YouTube_Trailer: String = "",
    val Rating: String = "",
    @SerializedName("Movie Poster") val Movie_Poster: String = "",
    val Director: String = "",
    val Writers: String = "",
    val Cast: String = ""
)
