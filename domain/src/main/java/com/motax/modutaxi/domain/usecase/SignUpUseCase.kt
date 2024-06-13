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
        phoneNumber: String,
        fcmToken: String
    ) = repository.signUp(key, name, gender, phoneNumber, fcmToken)
}