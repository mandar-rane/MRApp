package com.example.mrapp.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie-table")
data class RoomEntity(
    val Title: String = "",
    val Year: String = "",
    val Summary: String = "",
    @SerializedName("Short Summary") val Short_Summary: String = "",
    val Genres: String = "",
    @PrimaryKey
    val IMDBID: String = "",
    val Runtime: String = "",
    @SerializedName("YouTube Trailer")  val YouTube_Trailer: String = "",
    val Rating: String = "",
    @SerializedName("Movie Poster") val Movie_Poster: String = "",
    val Director: String = "",
    val Writers: String = "",
    val Cast: String = ""
)
