package com.example.fiwoapp.model.populartv

data class PopularTvResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)