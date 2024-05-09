package com.motax.modutaxi.presentation.ui.main.partylist.mapper

import com.motax.modutaxi.domain.model.TaxiPotData
import com.motax.modutaxi.presentation.ui.main.partylist.model.TaxiPotCategory
import com.motax.modutaxi.presentation.ui.main.partylist.model.UiTaxiPotItem


fun TaxiPotData.toUiTaxiPotItem(
    navigateToMatchDetail: (Long) -> Unit
) = UiTaxiPotItem(
    roomId = roomId,
    curHeadCount = 1,
    wishHeadCount = wishHeadcount,
    feePerPerson = "1000 원",
    recentChatTime = "3분전",
    departureDatetime = departureTime,
    categories = listOf(TaxiPotCategory.DEADLINE, TaxiPotCategory.FEMALES_ONLY),
    navigateToMatchDetail = navigateToMatchDetail
)