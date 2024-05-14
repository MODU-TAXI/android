package com.motax.modutaxi.presentation.ui

import com.motax.modutaxi.domain.model.AddressFromGeoItemData


fun AddressFromGeoItemData.toAddressString() = region.area1.name + " " + region.area2.name + " " + region.area3.name + " " + land.name +
            land.number1 + if(land.number2.isNotBlank()) "-" + land.number2 else ""


fun AddressFromGeoItemData.toBuildingName() = land.addition0.value