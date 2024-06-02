package com.motax.modutaxi.presentation.bindingadapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R

@BindingAdapter("keyword", "searchResult")
fun bindSearchResult(tv: TextView, keyword: String?, searchResult: String?) {

    keyword?.let {
        searchResult?.let {
            if (keyword.isNotBlank()) {
                val sIndex = searchResult.indexOf(keyword)
                if (sIndex != -1) {
                    val spannable = SpannableString(searchResult)
                        .apply {
                            setSpan(
                                ForegroundColorSpan(tv.context.getColor(R.color.mx_sub500)),
                                sIndex,
                                sIndex + keyword.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    tv.text = spannable;
                }
            }
        }
    }
}

@BindingAdapter("departureTimeTextStyle")
fun bindDepartureTimeTextStyle(tv: TextView, departureTime: String) {
    if (departureTime.isBlank()) {
        tv.typeface = Typeface.DEFAULT
    } else {
        tv.typeface = Typeface.DEFAULT_BOLD
    }
}