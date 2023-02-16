package com.example.mrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrapp.Adapters.MainPageAdapter
import com.example.mrapp.Api.ApiInterface
import com.example.mrapp.Models.Movie
import com.example.mrapp.Models.MovieApiResp
import com.example.mrapp.Models.RoomEntity
import com.example.mrapp.RoomDB.DAO
import com.example.mrapp.RoomDB.RoomApp
import com.example.mrapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var dao: DAO
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieArrayList: ArrayList<Movie>
    private lateinit var mainPageAdapter: MainPageAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = (application as RoomApp).db.movieDao()
        movieArrayList = arrayListOf()
        setMainPageRV()

        if (NetworkActivity().isOnline(this)) {
            fetchFromApi("1.json")
        } else {
            fetchFromRoomDB()
        }

        binding.refreshBtn.setOnClickListener {
            movieArrayList.clear()
            if (NetworkActivity().isOnline(this)) {
                fetchFromApi("2.json")
            } else {
                Toast.makeText(this, "You are OFFLINE", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun fetchFromApi(apiPath: String): Call<MovieApiResp> {
        Toast.makeText(this, "Data fetched from API", Toast.LENGTH_LONG).show()
        val movieResp: Call<MovieApiResp> = ApiInterface.apiService.retrofitInstance.getMovies(apiPath)
        movieResp.enqueue(object : Callback<MovieApiResp> {
            override fun onResponse(
                call: Call<MovieApiResp>,
                response: Response<MovieApiResp>
            ) {
                val movieResp: MovieApiResp? = response.body()
                if (movieResp != null) {
                    movieResp.Movie_List.distinct().forEach {
                        movieArrayList.add(
                            Movie(
                                it.Title,
                                it.Year,
                                it.Summary,
                                it.Short_Summary,
                                it.Genres,
                                it.IMDBID,
                                it.Runtime,
                                it.YouTube_Trailer,
                                it.Rating,
                                it.Movie_Poster,
                                it.Director,
                                it.Writers,
                                it.Cast,
                            )
                        )
                    }
                    updateLocalDB(movieResp)
                    setMainPageRV()
                } else {
                    Log.d("apiLog", "null")
                }
            }
            override fun onFailure(call: Call<MovieApiResp>, t: Throwable) {
                Log.d("apiLog", "Call Failed")
            }
        })
        return movieResp
    }
    private fun fetchFromRoomDB() {
        Toast.makeText(this, "Data fetched from ROOM Database", Toast.LENGTH_LONG).show()
        CoroutineScope(IO).launch {
            dao.getMoviesFromDB().collect {
                val fetchedList = ArrayList(it)
                addToList(fetchedList)
            }
        }
    }

    private fun addToList(fetchedList: java.util.ArrayList<RoomEntity>) {
        movieArrayList.clear()
        fetchedList.distinct().forEach {
            movieArrayList.add(
                Movie(
                    it.Title,
                    it.Year,
                    it.Summary,
                    it.Short_Summary,
                    it.Genres,
                    it.IMDBID,
                    it.Runtime,
                    it.YouTube_Trailer,
                    it.Rating,
                    it.Movie_Poster,
                    it.Director,
                    it.Writers,
                    it.Cast,
                )
            )
        }
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            setMainPageRV()
        })
    }

    private fun setMainPageRV() {
        recyclerView = findViewById(R.id.rv_main_page)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mainPageAdapter = MainPageAdapter(this, movieArrayList)
        recyclerView.adapter = mainPageAdapter
    }



    private fun updateLocalDB(movieresp: MovieApiResp?) {
        var movielist = movieresp!!.Movie_List.distinct()
        CoroutineScope(IO).launch {
            dao.nukeTable()
            movielist.forEach {
                dao.insertToDB(
                    RoomEntity(
                        it.Title,
                        it.Year,
                        it.Summary,
                        it.Short_Summary,
                        it.Genres,
                        it.IMDBID,
                        it.Runtime,
                        it.YouTube_Trailer,
                        it.Rating,
                        it.Movie_Poster,
                        it.Director,
                        it.Writers,
                        it.Cast,
                    )
                )
            }
        }
    }
}
