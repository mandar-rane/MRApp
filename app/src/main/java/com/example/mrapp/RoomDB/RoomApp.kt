package com.example.mrapp.RoomDB

import android.app.Application

class RoomApp: Application() {
    val db by lazy {
        MovieDatabase.getInstance(this)
    }
}