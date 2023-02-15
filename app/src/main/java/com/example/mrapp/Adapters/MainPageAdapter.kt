package com.example.mrapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mrapp.Models.Movie
import com.example.mrapp.R
import com.example.mrapp.databinding.ListItemMainBinding

class MainPageAdapter(private var context: Context, val movies: ArrayList<Movie>):
    RecyclerView.Adapter<MainPageAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val movieName: TextView = itemView.findViewById(R.id.tv_movie_name)
        val movieImage: ImageView = itemView.findViewById(R.id.iv_movie_image)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainPageAdapter.ViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.movieName.text = movie.Title
        Glide
            .with(context)
            .load(movie.Movie_Poster)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.movieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}