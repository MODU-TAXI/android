package com.motax.modutaxi.domain.usecase

import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IntroRepository
) {
    suspend operator fun invoke(
        type: String,
        accessToken: String
    ) = runCatching { repository.memberLogin(type, accessToken) }

}