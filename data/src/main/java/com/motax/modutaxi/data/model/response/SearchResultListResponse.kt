package com.motax.modutaxi.data.model.response

data class SearchResultListResponse(
    val result: List<SearchResultItem>
)

data class SearchResultItem(
    val title: String,
    val roadAddress: String,
    val mapX: String,
    val mapY: String
)