package com.motax.modutaxi.presentation.ui.main.chat.mapper

import com.motax.modutaxi.domain.model.AccountData
import com.motax.modutaxi.domain.model.AccountDataItem
import com.motax.modutaxi.presentation.ui.main.chat.model.UiAccountItem
import com.motax.modutaxi.presentation.util.Bank


fun AccountDataItem.toUiAccountItem(
    selectAccount: (Bank, String) -> Unit
) = UiAccountItem(
    bank = Bank.fromName(bank),
    account = accountNumber,
    selectAccount = selectAccount
)