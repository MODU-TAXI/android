package com.motax.modutaxi.domain.usecase

import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository : IntroRepository
) {
    suspend operator fun invoke(
        type: String,
        body: LoginRequestData
    ) : BaseState<AuthData> = repository.memberLogin(type,body)

}