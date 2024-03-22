package com.motax.modutaxi.presentation.bindingadapters

import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R

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