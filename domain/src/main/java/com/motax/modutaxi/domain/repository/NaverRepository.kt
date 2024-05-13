package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.SearchResultListData

interface NaverRepository {

    suspend fun getSearchResultList(
        keyword: String,
        display: Int
    ): Result<SearchResultListData>
}