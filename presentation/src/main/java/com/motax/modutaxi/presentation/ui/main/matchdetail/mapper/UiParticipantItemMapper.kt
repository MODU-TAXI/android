package com.motax.modutaxi.presentation.ui.main.matchdetail.mapper

import com.motax.modutaxi.domain.model.TaxiPotMemberData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem

fun TaxiPotMemberData.toUiParticipantItem(
    showProfile: (Long) -> Unit
) = UiParticipantItem(
    memberId = memberId,
    profileImage = imageUrl,
    nickname = nickname,
    matchingCount = "${matchingCount}회",
    certified = certified,
    thisIsMe = thisIsMe,
    showProfile = showProfile
)