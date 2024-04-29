package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.data.model.runRemote
import com.motax.modutaxi.data.remote.IntroApi
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    private val api: IntroApi
): IntroRepository {

    override suspend fun memberLogin(type: String, accessToken: String): BaseState<AuthData> {
        val response = runRemote { api.memberLogin(type, LoginRequest(accessToken)) }

    }

}