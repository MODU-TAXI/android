package com.motax.modutaxi.presentation.bindingadapters

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.motax.modutaxi.presentation.R

@BindingAdapter("chipItems")
fun setChipItems(chipGroup: ChipGroup, items: List<String>?) {
    items?.let {
        chipGroup.removeAllViews()

        val sortedItems = items.sortedByDescending { it == "STUDENT_CERTIFICATION" }

        for (item in sortedItems) {
            val chip = LayoutInflater.from(chipGroup.context).inflate(R.layout.item_chip, chipGroup, false) as Chip
            if(item == "STUDENT_CERTIFICATION") {
                chip.text = "학생인증"
                chip.setChipBackgroundColorResource(R.color.mx_sub100)
                chip.setTextColor(chipGroup.context.resources.getColor(R.color.mx_sub500))
            } else {
                chip.setChipBackgroundColorResource(R.color.mx_gray100)
                chip.setTextColor(chipGroup.context.resources.getColor(R.color.mx_gray500))
                if(item == "ONLY_WOMAN") chip.text = "여자만"
                if(item == "MANNER") chip.text = "조용히"
            }


            chipGroup.addView(chip)
        }
    }
}