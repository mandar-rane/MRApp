package com.example.mrapp.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mrapp.Models.RoomEntity

@Database(entities = [RoomEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): DAO

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}