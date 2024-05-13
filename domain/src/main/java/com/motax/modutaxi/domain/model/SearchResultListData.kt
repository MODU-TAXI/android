package com.motax.modutaxi.domain.model

data class SearchResultData(
    val title: String,
    val roadAddress: String
)

data class SearchResultListData(
    val results: List<SearchResultData>
)