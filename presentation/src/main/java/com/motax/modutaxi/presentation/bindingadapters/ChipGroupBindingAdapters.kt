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
        for (item in items) {
            val chip = LayoutInflater.from(chipGroup.context).inflate(R.layout.item_chip, chipGroup, false) as Chip
            chip.text = item
            chipGroup.addView(chip)
        }
    }
}