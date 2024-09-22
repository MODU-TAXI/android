package com.motax.modutaxi.presentation.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.motax.modutaxi.presentation.databinding.DialogBankBottomsheetBinding
import com.motax.modutaxi.presentation.util.Bank

class BankBottomSheetDialog(
    context: Context,
    private val selectBank : (Bank) -> Unit
): BottomSheetDialog(context) {

    private var binding: DialogBankBottomsheetBinding

    init{
        binding = DialogBankBottomsheetBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        setBottomSheetListener()
        setupRatio()
    }

    private fun setupRatio(){
        val bottomSheet = this.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setBottomSheetListener(){

        with(binding){
            val btnList = listOf(btnNh, btnKakao, btnGookmin, btnToss, btnSinhan, btnWoori, btnIbk, btnHana, btnSm, btnBusan, btnDaegu, btnK, btnSinhyup, btnPost, btnSc, btnGyungnam, btnGwangju, btnSuhyup,
                btnJunbuk, btnJuchuk, btnJeju)

            btnList.forEach { view ->
                view.setOnClickListener {
                    when(view){
                        btnNh -> selectBank(Bank.NH)
                        btnK -> selectBank(Bank.K)
                        btnKakao -> selectBank(Bank.KAKAO)
                        btnGookmin -> selectBank(Bank.KB)
                        btnToss -> selectBank(Bank.TOSS)
                        btnSinhan -> selectBank(Bank.SHINHAN)
                        btnWoori -> selectBank(Bank.WOORI)
                        btnIbk -> selectBank(Bank.IBK)
                        btnHana -> selectBank(Bank.HANA)
                        btnSm -> selectBank(Bank.MG)
                        btnBusan -> selectBank(Bank.BUSAN)
                        btnDaegu -> selectBank(Bank.DAEGU)
                        btnSinhyup -> selectBank(Bank.SHINHYUP)
                        btnPost -> selectBank(Bank.POST)
                        btnSc -> selectBank(Bank.SC)
                        btnGyungnam -> selectBank(Bank.BNK)
                        btnGwangju -> selectBank(Bank.GWANGJU)
                        btnSuhyup -> selectBank(Bank.SUHYUP)
                        btnJunbuk -> selectBank(Bank.JEONBUK)
                        btnJuchuk -> selectBank(Bank.SB)
                        btnJeju -> selectBank(Bank.JEJU)
                    }

                    dismiss()
                }
            }
        }
    }

}