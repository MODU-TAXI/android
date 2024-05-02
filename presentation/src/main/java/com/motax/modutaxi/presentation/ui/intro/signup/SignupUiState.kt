package com.motax.modutaxi.presentation.ui.intro.signup

sealed class AuthBtnState {
    data object Able : AuthBtnState()
    data class Disable(val msg: String) : AuthBtnState()
    data class AuthSuccess(val msg: String) : AuthBtnState()
    data class AuthFailure(val msg: String) : AuthBtnState()
}