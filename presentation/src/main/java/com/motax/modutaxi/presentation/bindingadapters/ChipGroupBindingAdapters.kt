package com.motax.modutaxi.presentation.bindingadapters

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.ChipGroup
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag


@BindingAdapter("taxiPotFilterChips")
fun setTaxiPotFilterChips(chipGroup: ChipGroup, items: List<RoomTag>) {
    chipGroup.removeAllViews()

    items.sortedByDescending { it == RoomTag.STUDENT_CERTIFICATION }.forEach {
        val chip = TextView(chipGroup.context).apply {
            text = it.uiText
        }

        when (it) {
            RoomTag.STUDENT_CERTIFICATION -> {
                chip.apply {
                    setBackgroundResource(R.drawable.rect_sub100fill_nostroke_4radius)
                    setTextAppearance(R.style.TextSmallMedium)
                    setTextColor(ContextCompat.getColor(chipGroup.context, R.color.mx_sub500))
                    setPadding(20, 10, 20, 10)
                }
            }

            else -> {
                chip.apply {
                    setBackgroundResource(R.drawable.rect_gray100fill_nostroke_4radius)
                    setTextAppearance(R.style.TextSmallMedium)
                    setTextColor(ContextCompat.getColor(chipGroup.context, R.color.mx_gray500))
                    setPadding(20, 10, 20, 10)
                }

            }
        }
        chip.text = it.uiText
        chipGroup.addView(chip)
    }
}