package com.motax.modutaxi.presentation.ui.main.chat.mapper

import com.motax.modutaxi.domain.model.PaymentMembersItemData
import com.motax.modutaxi.presentation.ui.main.chat.model.UiPaymentParticipantItem


fun PaymentMembersItemData.toUiPaymentParticipant(): UiPaymentParticipantItem {
    return UiPaymentParticipantItem(
        id = id,
        profile = imageUrl,
        name = name,
        state = status,
        thisIsMe = me,
        nickName = nickName
    )
}