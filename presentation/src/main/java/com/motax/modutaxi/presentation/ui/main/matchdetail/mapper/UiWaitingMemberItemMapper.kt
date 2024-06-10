package com.motax.modutaxi.presentation.ui.main.matchdetail.mapper

import com.motax.modutaxi.domain.model.TaxiPotMemberData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem

fun TaxiPotMemberData.toUiWaitingMemberItem(
    acceptParticipant: (Long) -> Unit
) = UiWaitingMemberItem(
    memberId = memberId,
    profileImage = imageUrl,
    nickname = nickname,
    matchingCount = "${matchingCount}회",
    certified = certified,
    thisIsMe = thisIsMe,
    acceptParticipant = acceptParticipant
)