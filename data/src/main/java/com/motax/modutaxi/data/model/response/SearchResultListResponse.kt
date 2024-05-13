package com.motax.modutaxi.data.model.response

data class SearchResultListResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<SearchResultItem>
)

data class SearchResultItem(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String,
    val roadAddress: String,
    val mapx: Long,
    val mapy: Long
)