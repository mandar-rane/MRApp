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

class MainPageAdapter(private var context: Context):
    RecyclerView.Adapter<MainPageAdapter.ViewHolder>(){
    class ViewHolder(val binding: ListItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
//        val movieName: TextView = itemView.findViewById(R.id.tv_movie_name)
//        val movieImage: ImageView = itemView.findViewById(R.id.iv_movie_image)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.IMDBID == newItem.IMDBID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var movies: List<Movie
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageAdapter.ViewHolder {
        return ViewHolder(ListItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MainPageAdapter.ViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.binding.tvMovieName.text = movie.Title
        Glide
            .with(context)
            .load(movie.Movie_Poster)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivMovieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}