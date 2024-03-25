package com.motax.modutaxi.presentation.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.intro.signup.phoneauth.PhoneAuthBtnState

@BindingAdapter("onboardQuestionBtnState")
fun bindOnboardQuestionBtnState(btn: AppCompatButton, isSelected : Boolean) {
    if(isSelected){
        btn.setBackgroundResource(R.drawable.rect_black_fill_nostroke_12radius)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
    } else {
        btn.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.black))
    }
}

@BindingAdapter("onboardPhoneAuthBtnState")
fun bindOnboardPhoneBtnState(btn : AppCompatButton, state: PhoneAuthBtnState){
    when(state){
        is PhoneAuthBtnState.Disable -> btn.isEnabled = false
        else -> btn.isEnabled = true
    }
}

@BindingAdapter("onboardPhoneAuthHelperText")
fun bindOnboardPhoneAuthHelperText(tv : TextView, state: PhoneAuthBtnState){
    when(state){
        is PhoneAuthBtnState.Able -> tv.visibility = View.GONE
        is PhoneAuthBtnState.AuthSuccess -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_blue2))
        }
        is PhoneAuthBtnState.AuthFailure -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }
        is PhoneAuthBtnState.Disable -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }
    }
}