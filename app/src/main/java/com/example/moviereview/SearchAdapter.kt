package com.example.moviereview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviereview.databinding.ItemMovieListBinding
import com.example.moviereview.network.MovieResponse

class SearchAdapter(private val itemClick : (MovieResponse.Item) -> Unit) :
    ListAdapter<MovieResponse.Item, SearchAdapter.SearchViewHolder>(SearchDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.onClick(getItem(position), itemClick)
    }

    class SearchViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: MovieResponse.Item) {
            binding.model = list
            binding.textviewMovieTitle.text = removeHtmlTag(list.title).toString()
            Glide.with(binding.imgMovie.context)
                .load(list.image)
                .into(binding.imgMovie)
        }

        fun onClick(list: MovieResponse.Item, itemClick: (MovieResponse.Item) -> Unit) {
            binding.textviewMovieMore.setOnClickListener {
                itemClick.invoke(list)
            }
        }

        private fun removeHtmlTag(html : String) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, 0).toString()
            } else {
                Log.e("Version Error", "")
            }
    }

    private class SearchDiffUtil : DiffUtil.ItemCallback<MovieResponse.Item>() {
        override fun areItemsTheSame(oldItem: MovieResponse.Item, newItem: MovieResponse.Item) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MovieResponse.Item, newItem: MovieResponse.Item) =
            oldItem.link == newItem.link
    }


}