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
        .load(if (imageUrl.isEmpty()) R.drawable.ic_person else imageUrl)
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