package com.motax.modutaxi.presentation.ui.main.mypage

object ProfileEditData {

    var key = ""
        private set

    var name = ""
    private set

    var gender = ""
    private set

    var phoneNumber = ""
    private set

    fun setKey(data: String) {
        key = data
    }

    fun setSignUpName(data : String){
        name = data
    }

    fun setSignUpGender(data : String){
        gender = data
    }

    fun setSignUpPhoneNumber(data: String){
        phoneNumber = data
    }
}