package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.request.MemberLoginRequest
import com.motax.modutaxi.data.model.response.MemberLoginAndSignupResponse
import com.motax.modutaxi.data.model.runRemote
import com.motax.modutaxi.data.remote.IntroApi
import com.motax.modutaxi.domain.model.BaseState
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    private val api: IntroApi
): IntroRepository {
    override suspend fun memberLogin(
        type: String,
        body: MemberLoginRequest
    ): BaseState<MemberLoginAndSignupResponse> = runRemote {
        api.memberLogin(type, body)
    }

}