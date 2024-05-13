package com.motax.modutaxi.presentation.bindingadapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.motax.modutaxi.presentation.R

@BindingAdapter("keyword", "searchResult")
fun bindSearchResult(tv: TextView, keyword: String?, searchResult: String?) {

    Log.d("debugging", "$keyword,$searchResult")
    keyword?.let {
        searchResult?.let {
            if(keyword.isNotBlank()) {
                val sIndex = searchResult.indexOf(keyword)
                if(sIndex != -1) {
                    val spannable = SpannableString(searchResult)
                        .apply {
                            setSpan(
                                ForegroundColorSpan(tv.context.getColor(R.color.search_result_match)),
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