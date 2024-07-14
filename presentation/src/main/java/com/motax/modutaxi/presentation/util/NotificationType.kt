package com.motax.modutaxi.presentation.util

import com.motax.modutaxi.presentation.R

enum class NotificationType(val message: String, val icResId: Int) {
    // 매칭
    PARTICIPATE_REQUEST("새로운 멤버가 매칭 대기중이에요!", R.drawable.ic_participate_request),
    MATCHING_SUCCESS("매칭이 수락되었어요! 지금 바로 채팅을 시작하세요.", R.drawable.ic_matching),
    MATCHING_COMPLETE("모든 인원이 모였어요. 매칭 완료를 눌러주세요.", R.drawable.ic_matching),

    // 신고
    REPORT_SUCCESS("신고가 접수되었어요, 빨리 해결해드릴게요!", R.drawable.ic_report_success),

    // 정산
    PAYMENT_REQUEST("정산 금액을 입력해주세요.", R.drawable.ic_pig),
    PAYMENT_REQUEST_COMPLETE("정산 요청이 들어왔어요. 금액을 확인해주세요.", R.drawable.ic_pig),
    PAYMENT_ALL_COMPLETE("정산이 완료되었어요!", R.drawable.ic_payment_complete);

    companion object {
        fun getIconResId(type: String): Int {
            return entries.find { it.name == type }!!.icResId
        }
    }
}