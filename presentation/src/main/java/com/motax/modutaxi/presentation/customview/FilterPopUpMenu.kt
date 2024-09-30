package com.motax.modutaxi.presentation.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.PopupWindow
import com.motax.modutaxi.presentation.databinding.DialogFilterPopupMenuBinding
import com.motax.modutaxi.presentation.ui.main.showparty.TaxiPotSortType
import com.motax.modutaxi.presentation.util.Constants
import kotlin.math.roundToInt

class FilterPopUpMenu(
    private val context: Context,
    private val curSortType: TaxiPotSortType,
    private inline val onClick: (TaxiPotSortType) -> Unit,
) {
    private val popUp by lazy {
        PopupWindow(
            binding.root,
            Constants.FILTER_POPUP_WIDTH_DP.toPx(context.resources),
            Constants.THREE_POPUP_HEIGHT_DP.toPx(context.resources)
        ).apply {
            elevation = 10f
        }
    }

    private val binding by lazy {
        DialogFilterPopupMenuBinding.inflate(LayoutInflater.from(context)).apply {
            with(this) {
                tvDistance.setOnClickListener {
                    onClick(TaxiPotSortType.DISTANCE)
                    dismiss()
                }

                tvNew.setOnClickListener {
                    onClick(TaxiPotSortType.NEW)
                    dismiss()
                }

                tvTalk.setOnClickListener {
                    onClick(TaxiPotSortType.ENDTIME)
                    dismiss()
                }
            }

            when(curSortType){
                TaxiPotSortType.ENDTIME -> {
                    tvTalk.setTextColor(Color.BLACK)
                }

                TaxiPotSortType.NEW ->{
                    tvNew.setTextColor(Color.BLACK)
                }

                TaxiPotSortType.DISTANCE ->{
                    tvDistance.setTextColor(Color.BLACK)
                }
            }
        }
    }

    fun show(xPosition: Int, yPosition: Int) {
        popUp.isOutsideTouchable = true
        popUp.showAtLocation(binding.root, Gravity.NO_GRAVITY, xPosition, yPosition)
    }

    fun dismiss() {
        popUp.dismiss()
    }

    private fun Int.toPx(resource: Resources) =
        (resource.displayMetrics.density * this).roundToInt()
}
