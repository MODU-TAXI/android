package com.motax.modutaxi.presentation.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.ItemTaxipotBinding

class TaxipotAdapter() : ListAdapter<Taxipot, TaxipotAdapter.ViewHolder>(TaxipotDiffCallback()) {

    class ViewHolder(private val binding: ItemTaxipotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taxipot: Taxipot) {
            binding.apply {
                chipGroupClassification.removeAllViews()
                taxipot.categories.forEach { category ->
                    val chip = Chip(chipGroupClassification.context)
                    chip.isClickable = false
                    chip.isCheckable = false

                    chip.text = getTextForCategory(category)

                    val backgroundColor = getBackgroundColorForCategory(category)
                    chip.chipBackgroundColor = ColorStateList.valueOf(backgroundColor)

                    val chipDrawable = chip.chipDrawable
                    chipDrawable?.setBounds(Rect(4, 4, 4, 4))


                    chip.setTextAppearanceResource(R.style.TextSmallMedium)
                    chip.setTextColor(getColorForCategory(category))


                    chip.chipStartPadding = 8f
                    chip.chipEndPadding = 8f
                    chip.chipStrokeWidth = 0f

                    chipGroupClassification.addView(chip)
                }
            }

            //플레이스 홀더로 변경...?
            binding.tvMinutesAgo.setText("${taxipot.minutesAgoChat}분 전 채팅")
            binding.tvParticipants.setText("${taxipot.currentParticipantCount}/${taxipot.maxParticipantsLimit}")
            binding.tvFeePerPerson.setText("인당 ${taxipot.feePerPerson}원")
            binding.tvRoute.setText(taxipot.route)
            binding.tvDepartureDatetime.setText(taxipot.departureDatetime)
        }

        private fun getBackgroundColorForCategory(category: Category): Int {
            return when (category) {
                Category.DEADLINE -> Color.parseColor("#FCE6E6")
                Category.STUDENT_VERIFICATION -> Color.parseColor("#EBFBF7")
                Category.FEMALES_ONLY -> Color.parseColor("#EBEBEB")
                Category.QUIET -> Color.parseColor("#EBEBEB")
            }
        }

        private fun getTextForCategory(category: Category): String {
            return when(category) {
                Category.DEADLINE -> "마감임박"
                Category.STUDENT_VERIFICATION -> "학생인증"
                Category.FEMALES_ONLY -> "여자만"
                Category.QUIET -> "조용히"
            }
        }

        private fun getColorForCategory(category: Category): Int {
            return when (category) {
                Category.DEADLINE -> Color.parseColor("#FF4949")
                Category.STUDENT_VERIFICATION -> Color.parseColor("#40CEAC")
                Category.FEMALES_ONLY -> Color.parseColor("#9C9C9C")
                Category.QUIET -> Color.parseColor("#9C9C9C")
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaxipotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taxipot = getItem(position)
        holder.bind(taxipot)
    }
}

class TaxipotDiffCallback : DiffUtil.ItemCallback<Taxipot>() {
    override fun areItemsTheSame(oldItem: Taxipot, newItem: Taxipot): Boolean {
        return oldItem.id == newItem.id // id가 같으면 같은 아이템으로 간주
    }

    override fun areContentsTheSame(oldItem: Taxipot, newItem: Taxipot): Boolean {
        return oldItem == newItem // 데이터 클래스이므로, equals를 사용해 내용이 같은지 비교
    }
}