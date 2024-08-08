package com.motax.modutaxi.presentation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.util.Bank
import com.motax.modutaxi.presentation.util.NotificationType

@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    if (url.isNotBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter("profileImgUrl")
fun bindProfileImg(imageView: ImageView, url: String?) {
    val imageUrl = url?: ""
    Glide.with(imageView.context)
        .load(imageUrl.ifEmpty { R.drawable.ic_no_person_profile })
        .error(R.drawable.ic_person)
        .circleCrop()
        .into(imageView)
}

@BindingAdapter("bankLogo")
fun setBankLogo(imageView: ImageView, bankName: String) {
    val logoResId = Bank.getLogoResource(bankName)
    imageView.setImageResource(logoResId)
}

@BindingAdapter("imageFromType")
fun setImageFromType(view: ImageView, type: String) {
    val drawableResId = NotificationType.getIconResId(type)
    view.setImageResource(drawableResId)
}

@BindingAdapter("roomStatus")
fun setRoomStatus(iv: ImageView, type: String){
    when(type){
        "BEFORE_MATCHING" -> {
            iv.setImageResource(R.drawable.group_matching)
        }

        "AFTER_MATCHING" -> {
            iv.setImageResource(R.drawable.group_matching_complete)
        }

        "BEFORE_PAYMENT" -> {
            iv.setImageResource(R.drawable.group_calculating)
        }

        "AFTER_PAYMENT" -> {
            iv.setImageResource(R.drawable.group_calculate_complete)
        }

        else -> {}
    }
}