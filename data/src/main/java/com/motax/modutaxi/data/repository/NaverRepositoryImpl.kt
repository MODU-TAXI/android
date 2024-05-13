package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.model.response.SearchResultListResponse
import com.motax.modutaxi.data.remote.NaverApi
import com.motax.modutaxi.domain.model.SearchResultListData
import com.motax.modutaxi.domain.repository.NaverRepository
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val api: NaverApi
) : NaverRepository {

    override suspend fun getSearchResultList(keyword: String, display: Int): Result<SearchResultListData> {
        return runCatching {
            api.getSearchResultList(keyword, display)
        }.mapCatching { response ->
            response.toDomain()
        }
    }
}