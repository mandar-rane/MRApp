package com.example.mrapp.Models

import com.google.gson.annotations.SerializedName

data class MovieApiResp(@SerializedName("Movie List") val Movie_List: List<Movie>)
