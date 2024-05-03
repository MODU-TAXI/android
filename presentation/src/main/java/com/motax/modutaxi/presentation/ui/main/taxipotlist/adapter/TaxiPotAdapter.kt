package com.motax.modutaxi.presentation.ui.main.taxipotlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemTaxipotBinding
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.UiTaxiPotItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil


class TaxiPotAdapter :
    ListAdapter<UiTaxiPotItem, TaxiPotViewHolder>(DefaultDiffUtil<UiTaxiPotItem>()) {

    override fun onBindViewHolder(holder: TaxiPotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiPotViewHolder =
        TaxiPotViewHolder(
            ItemTaxipotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

}


class TaxiPotViewHolder(private val binding: ItemTaxipotBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiTaxiPotItem) {
        binding.item = item
        binding.root.setOnClickListener {
            item.enterPot(item.roomId)
        }
    }
}

//class TaxipotAdapter() : ListAdapter<UiTaxiPotItem, TaxipotAdapter.ViewHolder>(TaxipotDiffCallback()) {
//
//    class ViewHolder(private val binding: ItemTaxipotBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(uiTaxiPotItem: UiTaxiPotItem) {
//            binding.apply {
//                chipGroupClassification.removeAllViews()
//                uiTaxiPotItem.categories.forEach { category ->
//                    val textView = TextView(chipGroupClassification.context).apply {
//                        text = getTextForCategory(category)
//
//                        //배경
//                        val backgroundResId = getBackgroundDrawableForCategory(category)
//                        background = ContextCompat.getDrawable(context, backgroundResId)
//
//                        //스타일
//                        setTextAppearance(R.style.TextSmallMedium)
//                        setTextColor(getColorForCategory(category))
//
//                        //패딩 설정
//                        //dp -> px 변환
//                        val context = chipGroupClassification.context
//                        val density = context.resources.displayMetrics.density
//
//                        val leftRightPaddingInPx = (8 * density).toInt()
//                        val topBottomPaddingInPx = (4 * density).toInt()
//
//                        setPadding(leftRightPaddingInPx, topBottomPaddingInPx, leftRightPaddingInPx, topBottomPaddingInPx)
//
//                    }
//                    chipGroupClassification.addView(textView)
//                }
//            }
//
//            //플레이스 홀더로 변경...?
//            binding.tvMinutesAgo.setText("${uiTaxiPotItem.recentChatTime}분 전 채팅")
//            binding.tvParticipants.setText("${uiTaxiPotItem.curHeadCount}/${uiTaxiPotItem.wishHeadCount}")
//            binding.tvFeePerPerson.setText("인당 ${uiTaxiPotItem.feePerPerson}원")
//            binding.tvRoute.setText(uiTaxiPotItem.route)
//            binding.tvDepartureDatetime.setText(uiTaxiPotItem.departureDatetime)
//        }
//
//        private fun getBackgroundDrawableForCategory(taxipotCategory: TaxiPotCategory): Int {
//            return when (taxipotCategory) {
//                TaxiPotCategory.DEADLINE -> R.drawable.rect_red_fill_nostroke_4radius
//                TaxiPotCategory.STUDENT_VERIFICATION -> R.drawable.rect_mint_fill_nostroke_4radius
//                TaxiPotCategory.FEMALES_ONLY -> R.drawable.rect_grey0_fill_nostroke_4radius
//                TaxiPotCategory.QUIET -> R.drawable.rect_grey0_fill_nostroke_4radius
//            }
//        }
//
//        private fun getTextForCategory(taxipotCategory: TaxiPotCategory): String {
//            return when(taxipotCategory) {
//                TaxiPotCategory.DEADLINE -> "마감임박"
//                TaxiPotCategory.STUDENT_VERIFICATION -> "학생인증"
//                TaxiPotCategory.FEMALES_ONLY -> "여자만"
//                TaxiPotCategory.QUIET -> "조용히"
//            }
//        }
//
//        private fun getColorForCategory(taxipotCategory: TaxiPotCategory): Int {
//            return when (taxipotCategory) {
//                TaxiPotCategory.DEADLINE -> Color.parseColor("#FF4949")
//                TaxiPotCategory.STUDENT_VERIFICATION -> Color.parseColor("#40CEAC")
//                TaxiPotCategory.FEMALES_ONLY -> Color.parseColor("#9C9C9C")
//                TaxiPotCategory.QUIET -> Color.parseColor("#9C9C9C")
//            }
//        }
//
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemTaxipotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val taxipot = getItem(position)
//        holder.bind(taxipot)
//    }
//}
//
//class TaxipotDiffCallback : DiffUtil.ItemCallback<UiTaxiPotItem>() {
//    override fun areItemsTheSame(oldItem: UiTaxiPotItem, newItem: UiTaxiPotItem): Boolean {
//        return oldItem.roomId == newItem.roomId // id가 같으면 같은 아이템으로 간주
//    }
//
//    override fun areContentsTheSame(oldItem: UiTaxiPotItem, newItem: UiTaxiPotItem): Boolean {
//        return oldItem == newItem // 데이터 클래스이므로, equals를 사용해 내용이 같은지 비교
//    }
//}