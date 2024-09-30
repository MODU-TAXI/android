package com.motax.modutaxi.presentation.bindingadapters

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.intro.signup.AuthBtnState
import com.motax.modutaxi.presentation.ui.main.matchdetail.RoomState
import com.motax.modutaxi.presentation.ui.main.mypage.MyPageAuthBtnState

@BindingAdapter("onboardQuestionBtnState")
fun bindOnboardQuestionBtnState(btn: AppCompatButton, isSelected: Boolean) {
    if (isSelected) {
        btn.setBackgroundResource(R.drawable.rect_nofill_gray900stroke_12radius)
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

@BindingAdapter("myPagePhoneAuthBtnState")
fun bindMyPagePhoneAuthBtnState(btn: AppCompatButton, state: MyPageAuthBtnState) {
    when (state) {
        is MyPageAuthBtnState.Disable -> btn.isEnabled = false
        else -> btn.isEnabled = true
    }
}

@BindingAdapter("myPagePhoneAuthHelperText")
fun bindMyPagePhoneAuthHelperText(tv: TextView, state: MyPageAuthBtnState) {
    when (state) {
        is MyPageAuthBtnState.Able -> tv.visibility = View.GONE
        is MyPageAuthBtnState.AuthSuccess -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_blue2))
        }

        is MyPageAuthBtnState.AuthFailure -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }

        is MyPageAuthBtnState.Disable -> {
            tv.visibility = View.VISIBLE
            tv.text = state.msg
            tv.setTextColor(ContextCompat.getColor(tv.context, R.color.mx_red))
        }
    }
}

@BindingAdapter("matchingParticipateBtnState")
fun bindMatchingParticipateBtnState(btn: AppCompatButton, roomState: RoomState) {
    when (roomState) {
        RoomState.PARTICIPANT, RoomState.OWNER -> {
            btn.setBackgroundResource(R.drawable.rect_nofill_sub500stroke_61radius)
            btn.setTextColor(ContextCompat.getColor(btn.context, R.color.mx_sub500))
            btn.text = "채팅방 입장하기"
        }

        RoomState.WAITING -> {
            btn.setBackgroundResource(R.drawable.rect_gray600fill_nostroke_61radius)
            btn.setTextColor(ContextCompat.getColor(btn.context, R.color.mx_sub500))
            btn.text = "대기 취소하기"
        }

        RoomState.NOTHING -> {
            btn.setBackgroundResource(R.drawable.rect_sub500fill_nostroke_61radius)
            btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
            btn.text = "매칭 참여하기"
        }

        else -> {}
    }
}

@BindingAdapter("btnRoomStatus", "btnIsManager")
fun bindBtnRoomStatus(btn: AppCompatButton, type: String, isManager:Boolean) {
    when (type) {
        "BEFORE_MATCHING" -> {
            btn.text = "매칭완료"
            if(isManager){
                btn.visibility = View.VISIBLE
            } else {
                btn.visibility = View.GONE
            }
        }

        "AFTER_MATCHING" -> {
            btn.text = "정산하기"

            if(isManager){
                btn.visibility = View.VISIBLE
            } else {
                btn.visibility = View.GONE
            }
        }


        "BEFORE_PAYMENT" -> {
            btn.text = "정산현황"
            btn.visibility = View.VISIBLE
            if(isManager){
                btn.text = "정산현황"
            } else {
                btn.text = "정산하기"
            }
        }

        else -> {}
    }
}

@BindingAdapter("reportBtnState")
fun bindReportBtnState(btn: AppCompatButton, isButtonEnabled: Boolean) {
    btn.isEnabled = isButtonEnabled
    if (isButtonEnabled) {
        btn.setBackgroundResource(R.drawable.rect_sub500fill_nostroke_61radius)
    } else {
        btn.setBackgroundResource(R.drawable.rect_gray300fill_nostroke_61radius)
    }
}

@BindingAdapter("backgroundColorBasedOnChecked")
fun setBackgroundColorBasedOnChecked(view: View, checked: Boolean) {
    val color = if (!checked) {
        Color.parseColor("#F5F8FF")
    } else {
        Color.parseColor("#FFFFFF")
    }
    view.setBackgroundColor(color)
}