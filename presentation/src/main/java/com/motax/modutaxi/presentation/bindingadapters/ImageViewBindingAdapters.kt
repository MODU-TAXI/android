package com.motax.modutaxi.presentation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.motax.modutaxi.presentation.R

@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    if (url.isNotBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter("profileImgUrl")
fun bindProfileImg(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.ic_person)
        .circleCrop()
        .into(imageView)
}