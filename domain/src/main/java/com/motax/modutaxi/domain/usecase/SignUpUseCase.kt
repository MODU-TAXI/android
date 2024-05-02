package com.motax.modutaxi.domain.usecase

import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: IntroRepository
) {
    suspend operator fun invoke(
        key: String,
        name: String,
        gender: String,
        phoneNumber: String
    ) = repository.signUp(key, name, gender, phoneNumber)
}