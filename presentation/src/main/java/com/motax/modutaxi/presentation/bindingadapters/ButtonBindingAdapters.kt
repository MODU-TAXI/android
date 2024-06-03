package com.motax.modutaxi.presentation.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.intro.signup.AuthBtnState

@BindingAdapter("onboardQuestionBtnState")
fun bindOnboardQuestionBtnState(btn: AppCompatButton, isSelected: Boolean) {
    if (isSelected) {
        btn.setBackgroundResource(R.drawable.rect_black_fill_nostroke_12radius)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
    } else {
        btn.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.black))
    }
}

@BindingAdapter("onboardPhoneAuthBtnState")
fun bindOnboardPhoneBtnState(btn: AppCompatButton, state: AuthBtnState) {
    when (state) {
        is AuthBtnState.Disable -> btn.isEnabled = false
        else -> btn.isEnabled = true
    }
}

@BindingAdapter("onboardPhoneAuthHelperText")
fun bindOnboardPhoneAuthHelperText(tv: TextView, state: AuthBtnState) {
    when (state) {
        is AuthBtnState.Able -> tv.visibility = View.GONE
        is AuthBtnState.AuthSuccess -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_blue2))
        }

        is AuthBtnState.AuthFailure -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }

        is AuthBtnState.Disable -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }
    }
}

@BindingAdapter("matchingParticipateBtnState")
fun bindMatchingParticipateBtnState(btn: AppCompatButton, myRoom: Boolean) {
    if (myRoom) {
        btn.setBackgroundResource(R.drawable.rect_nofill_sub500stroke_61radius)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.mx_sub500))
    } else {
        btn.setBackgroundResource(R.drawable.rect_sub500fill_nostroke_61radius)
        ContextCompat.getColor(btn.context, R.color.white)
    }
}
