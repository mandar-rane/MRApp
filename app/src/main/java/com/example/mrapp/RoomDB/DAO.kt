package com.example.mrapp.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mrapp.Models.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert
    suspend fun insertToDB(roomEntity: RoomEntity)

    @Query("SELECT * FROM `movie-table`")
    fun getMoviesFromDB(): Flow<List<RoomEntity>>

    @Query("DELETE FROM `movie-table`")
    fun nukeTable()

}