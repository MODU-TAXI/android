package com.motax.modutaxi.domain.model

data class SearchResultListData (
    val result: List<SearchResultData>
)

data class SearchResultData(
    val title: String,
    val roadAddress: String,
    val mapX: String,
    val mapY: String
)