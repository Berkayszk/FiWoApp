package com.example.fiwoapp.model.popularpeople

data class PopularPeopleResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)