package com.example.fiwoapp.model.popularmovie

data class PopularResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)