package com.motax.modutaxi.domain.model

data class SearchResultData(
    val title: String,
    val roadAddress: String,
    val mapx: Long,
    val mapy: Long
)

data class SearchResultListData(
    val results: List<SearchResultData>
)