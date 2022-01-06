package com.example.moviereview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviereview.databinding.ItemReviewListBinding
import com.example.moviereview.network.MovieResponse

class ReviewAdapter :
    ListAdapter<ReviewModel, ReviewAdapter.ReviewViewHolder>(ReviewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder =
        ReviewViewHolder(
            ItemReviewListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReviewViewHolder(private val binding: ItemReviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewModel) {
            Glide.with(binding.imgReview.context)
                .load(data.items!!.image)
                .into(binding.imgReview)
            binding.ratingReview.rating = data.rating
            binding.textViewReview.text = data.review
        }
    }

    private class ReviewDiffUtil : DiffUtil.ItemCallback<ReviewModel>() {
        override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel) =
            oldItem == newItem
    }

}