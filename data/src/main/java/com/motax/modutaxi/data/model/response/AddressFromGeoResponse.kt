package com.motax.modutaxi.data.model.response

data class AddressFromGeoResponse(
    val results: List<AddressFromGeoItem>,
)

data class AddressFromGeoItem(
    val name: String,
    val code: AddressFromGeoCode,
    val region: AddressFromGeoRegion,
    val land: AddressFromGeoLand
)

data class AddressFromGeoCode(
    val id: String,
    val mappingId: String,
    val type: String
)

data class AddressFromGeoRegion(
    val area0: AddressFromGeoAreaItem,
    val area1: AddressFromGeoAreaItem,
    val area2: AddressFromGeoAreaItem,
    val area3: AddressFromGeoAreaItem,
    val area4: AddressFromGeoAreaItem
)

data class AddressFromGeoLand(
    val type: String,
    val number1: String,
    val number2: String,
    val addition0: AddressFromGeoLandItem,
    val addition1: AddressFromGeoLandItem,
    val addition2: AddressFromGeoLandItem,
    val addition3: AddressFromGeoLandItem,
    val addition4: AddressFromGeoLandItem,
    val name: String
)

data class AddressFromGeoLandItem(
    val type: String,
    val value: String
)

data class AddressFromGeoAreaItem(
    val name: String
)

