package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.SearchResultListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {

    @GET("/v1/search/local.json")
    suspend fun getSearchResultList(
        @Query("query") keyword: String,
        @Query("display") display: Int,
    ): SearchResultListResponse
}