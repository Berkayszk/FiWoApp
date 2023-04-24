package com.example.fiwoapp.model.similartv

data class SimilarTvResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)