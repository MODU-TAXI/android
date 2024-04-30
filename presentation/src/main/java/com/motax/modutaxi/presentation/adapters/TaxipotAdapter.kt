package com.motax.modutaxi.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.ItemTaxipotBinding

class TaxipotAdapter() : ListAdapter<Taxipot, TaxipotAdapter.ViewHolder>(TaxipotDiffCallback()) {

    class ViewHolder(private val binding: ItemTaxipotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taxipot: Taxipot) {
            binding.apply {
                chipGroupClassification.removeAllViews()
                taxipot.categories.forEach { category ->
                    val textView = TextView(chipGroupClassification.context).apply {
                        text = getTextForCategory(category)

                        //배경
                        val backgroundResId = getBackgroundDrawableForCategory(category)
                        background = ContextCompat.getDrawable(context, backgroundResId)

                        //스타일
                        setTextAppearance(R.style.TextSmallMedium)
                        setTextColor(getColorForCategory(category))

                        //패딩 설정
                        //dp -> px 변환
                        val context = chipGroupClassification.context
                        val density = context.resources.displayMetrics.density

                        val leftRightPaddingInPx = (8 * density).toInt()
                        val topBottomPaddingInPx = (4 * density).toInt()

                        setPadding(leftRightPaddingInPx, topBottomPaddingInPx, leftRightPaddingInPx, topBottomPaddingInPx)

                    }
                    chipGroupClassification.addView(textView)
                }
            }

            //플레이스 홀더로 변경...?
            binding.tvMinutesAgo.setText("${taxipot.minutesAgoChat}분 전 채팅")
            binding.tvParticipants.setText("${taxipot.currentParticipantCount}/${taxipot.maxParticipantsLimit}")
            binding.tvFeePerPerson.setText("인당 ${taxipot.feePerPerson}원")
            binding.tvRoute.setText(taxipot.route)
            binding.tvDepartureDatetime.setText(taxipot.departureDatetime)
        }

        private fun getBackgroundDrawableForCategory(taxipotCategory: TaxipotCategory): Int {
            return when (taxipotCategory) {
                TaxipotCategory.DEADLINE -> R.drawable.rect_red_fill_nostroke_4radius
                TaxipotCategory.STUDENT_VERIFICATION -> R.drawable.rect_mint_fill_nostroke_4radius
                TaxipotCategory.FEMALES_ONLY -> R.drawable.rect_grey0_fill_nostroke_4radius
                TaxipotCategory.QUIET -> R.drawable.rect_grey0_fill_nostroke_4radius
            }
        }

        private fun getTextForCategory(taxipotCategory: TaxipotCategory): String {
            return when(taxipotCategory) {
                TaxipotCategory.DEADLINE -> "마감임박"
                TaxipotCategory.STUDENT_VERIFICATION -> "학생인증"
                TaxipotCategory.FEMALES_ONLY -> "여자만"
                TaxipotCategory.QUIET -> "조용히"
            }
        }

        private fun getColorForCategory(taxipotCategory: TaxipotCategory): Int {
            return when (taxipotCategory) {
                TaxipotCategory.DEADLINE -> Color.parseColor("#FF4949")
                TaxipotCategory.STUDENT_VERIFICATION -> Color.parseColor("#40CEAC")
                TaxipotCategory.FEMALES_ONLY -> Color.parseColor("#9C9C9C")
                TaxipotCategory.QUIET -> Color.parseColor("#9C9C9C")
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