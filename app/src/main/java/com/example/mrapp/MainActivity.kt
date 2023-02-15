package com.example.mrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrapp.Adapters.MainPageAdapter
import com.example.mrapp.Api.ApiInterface
import com.example.mrapp.Models.Movie
import com.example.mrapp.Models.MovieApiResp
import com.example.mrapp.Models.RoomEntity
import com.example.mrapp.RoomDB.DAO
import com.example.mrapp.RoomDB.RoomApp
import com.example.mrapp.Utils.RetrofitInstance
import com.example.mrapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var dao: DAO
    private lateinit var recyclerView: RecyclerView

    //private lateinit var movieArrayList: ArrayList<Movie>
    private lateinit var mainPageAdapter: MainPageAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = (application as RoomApp).db.movieDao()



        setMainPageRV()

        fetchFromApi()

    }

    private fun setMainPageRV() = binding.rvMainPage.apply {
        mainPageAdapter = MainPageAdapter(this@MainActivity)
        adapter = mainPageAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
//        recyclerView = findViewById(R.id.rv_main_page)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
//        MainPageAdapter = MainPageAdapter(this)
//        recyclerView.adapter = MainPageAdapter
    }

    private fun fetchFromApi() {
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getMovies()
            } catch (e: IOException) {
                Log.d("errty", "no Internet")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d("errty", "http error")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                mainPageAdapter.movies = response.body()!!.Movie_List
                CoroutineScope(IO).launch {
                    dao.nukeTable()
                }

                response.body()!!.Movie_List.forEach {
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
                            it.Cast
                        )
                    )
                }
                Log.d("errty", "${response.body()!!.Movie_List}")
            } else {
                Log.d("errty", "respose no successfull")
            }
        }
//        val movieResp: Call<MovieApiResp> = ApiInterface.apiService.retrofitInstance.getMovies()
//        movieResp.enqueue(object : Callback<MovieApiResp> {
//            override fun onResponse(
//                call: Call<MovieApiResp>,
//                response: Response<MovieApiResp>
//            ) {
//                val movieResp: MovieApiResp? = response.body()
//                if (movieResp != null) {
//                    movieResp.Movie_List.forEach {
//                        movieArrayList.add(
//                            Movie(
//                                it.Title,
//                                "",
//                                "",
//                                "",
//                                "",
//                                "",
//                                "",
//                                "",
//                                it.Movie_Poster,
//                                "",
//                                "",
//                                ""
//                            )
//                        )
//                    }
//                    setMainPageRV()
//                    Log.d("TypesenseLog", movieArrayList.size.toString())
//                } else {
//                    Log.d("TypesenseLog", "null")
//                }
//            }
//
//            override fun onFailure(call: Call<MovieApiResp>, t: Throwable) {
//                Log.d("TypesenseLog", "$t")
//            }
//        })
//        return movieResp
    }
}
