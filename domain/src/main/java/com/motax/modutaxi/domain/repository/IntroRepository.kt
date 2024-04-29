package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.BaseState

interface IntroRepository {

    suspend fun memberLogin(
        type: String,
        accessToken: String
    ): BaseState<AuthData>
}