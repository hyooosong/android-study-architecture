package com.example.moviereview.data.remote

data class MovieResponse(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
) {
    data class Item(
        val actor: String,
        val director: String,
        val image: String,
        val link: String,
        val pubDate: String,
        val subtitle: String,
        var title: String,
        val userRating: String
    )
}