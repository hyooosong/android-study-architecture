package com.example.moviereview.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereview.databinding.ItemMovieListBinding
import com.example.moviereview.data.remote.MovieResponse

class SearchAdapter(
    private val itemClick: (MovieResponse.Item) -> Unit,
    private val showDialog: (MovieResponse.Item) -> Unit
) :
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
        holder.bind(currentList[position])
        holder.onClickMore(getItem(position), itemClick)
        holder.showReviewDialog(getItem(position), showDialog)
    }

    class SearchViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: MovieResponse.Item) {
            binding.model = list
        }

        fun onClickMore(list: MovieResponse.Item, itemClick: (MovieResponse.Item) -> Unit) {
            binding.textviewMovieMore.setOnClickListener { itemClick.invoke(list) }
        }

        fun showReviewDialog(list: MovieResponse.Item, itemClick: (MovieResponse.Item) -> Unit) {
            itemView.setOnClickListener { itemClick.invoke(list) }
        }
    }

    private class SearchDiffUtil : DiffUtil.ItemCallback<MovieResponse.Item>() {
        override fun areItemsTheSame(oldItem: MovieResponse.Item, newItem: MovieResponse.Item) =
            oldItem.link == newItem.link

        override fun areContentsTheSame(oldItem: MovieResponse.Item, newItem: MovieResponse.Item) =
            oldItem == newItem
    }
}