package com.motax.modutaxi.presentation.ui.main.chat.mapper

import com.motax.modutaxi.domain.model.TaxiPotMemberData
import com.motax.modutaxi.domain.model.TaxiPotParticipantsData
import com.motax.modutaxi.presentation.ui.main.chat.model.UiCalculateParticipantItem

fun TaxiPotMemberData.toUiCalculateParticipant(
    changeParticipantState : (UiCalculateParticipantItem) -> Unit,
) =
    UiCalculateParticipantItem(
        memberId = memberId,
        profileImage = imageUrl,
        nickname = nickname,
        thisIsMe = thisIsMe,
        changeParticipantState = changeParticipantState
    )