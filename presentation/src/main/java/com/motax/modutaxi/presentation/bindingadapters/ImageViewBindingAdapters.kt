package com.motax.modutaxi.presentation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    if (url.isNotBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}