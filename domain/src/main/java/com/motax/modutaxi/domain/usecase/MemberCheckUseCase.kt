package com.motax.modutaxi.domain.usecase

import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class MemberCheckUseCase @Inject constructor(
    private val repository: IntroRepository
) {

    suspend operator fun invoke(
        type: String,
        accessToken: String
    ) = runCatching { repository.memberCheck(type, accessToken) }
}