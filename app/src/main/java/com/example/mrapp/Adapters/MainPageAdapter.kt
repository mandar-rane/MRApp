package com.example.mrapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mrapp.Models.Movie
import com.example.mrapp.R
import org.w3c.dom.Text

class MainPageAdapter(private var context: Context, val movies: ArrayList<Movie>):
    RecyclerView.Adapter<MainPageAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val movieName: TextView = itemView.findViewById(R.id.movie_title)
        val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
        val movieCast: TextView = itemView.findViewById(R.id.movie_cast)
        val movieRuntime: TextView = itemView.findViewById(R.id.movie_runtime)
        val movieYear: TextView = itemView.findViewById(R.id.movie_year)
        val expand_btn: ImageView = itemView.findViewById(R.id.arrow)
        val summary: LinearLayout = itemView.findViewById(R.id.summary_ll)
        val movie_summ: TextView = itemView.findViewById(R.id.movie_summary)
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainPageAdapter.ViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.movieName.text = movie.Title
        holder.movieCast.text = movie.Cast
        holder.movieRuntime.text = "${movie.Runtime} mins"
        holder.movieYear.text = movie.Year
        holder.movie_summ.text = movie.Short_Summary
        var isExpanded: Boolean = false
        holder.expand_btn.setOnClickListener {
            if (isExpanded){
                holder.summary.visibility = View.GONE
                holder.expand_btn.rotation = 0.0F
                isExpanded = false
            }else{
                holder.summary.visibility = View.VISIBLE
                holder.expand_btn.rotation = 180.0F
                isExpanded = true
            }

        }
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