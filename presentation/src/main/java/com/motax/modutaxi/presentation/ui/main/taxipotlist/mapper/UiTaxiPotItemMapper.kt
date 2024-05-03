package com.motax.modutaxi.presentation.ui.main.taxipotlist.mapper

import com.motax.modutaxi.domain.model.TaxiPotData
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.TaxiPotCategory
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.UiTaxiPotItem


fun TaxiPotData.toUiTaxiPotItem(
    enterPot: (Long) -> Unit
) = UiTaxiPotItem(
    roomId = roomId,
    curHeadCount = 1,
    wishHeadCount = wishHeadcount,
    feePerPerson = "1000 원",
    recentChatTime = "3분전",
    departureDatetime = departureTime,
    categories = listOf(TaxiPotCategory.DEADLINE, TaxiPotCategory.FEMALES_ONLY),
    enterPot = enterPot
)