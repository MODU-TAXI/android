package com.motax.modutaxi.presentation.ui.main.mypage

sealed class MyPageAuthBtnState {
    data object Able : MyPageAuthBtnState()
    data class Disable(val msg: String) : MyPageAuthBtnState()
    data class AuthSuccess(val msg: String) : MyPageAuthBtnState()
    data class AuthFailure(val msg: String) : MyPageAuthBtnState()
}