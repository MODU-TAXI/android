package com.motax.modutaxi.presentation.customview

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import com.motax.modutaxi.presentation.databinding.DialogEditDeletePopupMenuBinding
import com.motax.modutaxi.presentation.util.Constants
import kotlin.math.roundToInt
import android.widget.PopupWindow

class EditDeletePopUpMenu(
    private val context: Context,
    private inline val onClickEdit: () -> Unit,
    private inline val onClickDelete: () -> Unit,
) {
    private val popUp by lazy {
        PopupWindow(
            binding.root,
            Constants.POPUP_WIDTH_DP.toPx(context.resources),
            Constants.TWO_POPUP_HEIGHT_DP.toPx(context.resources)
        ).apply {
            elevation = 10f
        }
    }

    private val binding by lazy {
        DialogEditDeletePopupMenuBinding.inflate(LayoutInflater.from(context)).apply {
            with(this) {

                tvEdit.setOnClickListener {
                    onClickEdit()
                    dismiss()
                }

                tvDelete.setOnClickListener {
                    onClickDelete()
                    dismiss()
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