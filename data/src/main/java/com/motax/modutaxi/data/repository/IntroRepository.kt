package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.request.MemberLoginRequest
import com.motax.modutaxi.data.model.response.MemberLoginAndSignupResponse
import com.motax.modutaxi.domain.model.BaseState

interface IntroRepository {

    suspend fun memberLogin(
        type: String,
        body: MemberLoginRequest
    ): BaseState<MemberLoginAndSignupResponse>
}