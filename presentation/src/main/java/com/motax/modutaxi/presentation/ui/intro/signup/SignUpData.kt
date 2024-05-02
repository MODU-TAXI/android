package com.motax.modutaxi.presentation.ui.intro.signup

object SignUpData {
    var key = ""
    private set

    var name = ""
    private set

    var gender = ""
    private set

    var phoneNumber = ""
    private set

    fun setSignUpKey(data : String){
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