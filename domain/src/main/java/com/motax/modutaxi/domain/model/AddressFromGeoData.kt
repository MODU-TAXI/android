package com.motax.modutaxi.domain.model

data class AddressFromGeoData(
    val results: List<AddressFromGeoItemData>,
)

data class AddressFromGeoItemData(
    val name: String,
    val code: AddressFromGeoCodeData,
    val region: AddressFromGeoRegionData,
    val land: AddressFromGeoLandData
)

data class AddressFromGeoCodeData(
    val id: String,
    val mappingId: String,
    val type: String
)

data class AddressFromGeoRegionData(
    val area0: AddressFromGeoAreaData,
    val area1: AddressFromGeoAreaData,
    val area2: AddressFromGeoAreaData,
    val area3: AddressFromGeoAreaData,
    val area4: AddressFromGeoAreaData,
)

data class AddressFromGeoLandData(
    val type: String,
    val number1: String,
    val number2: String,
    val addition0: AddressFromGeoLandItemData,
    val addition1: AddressFromGeoLandItemData,
    val addition2: AddressFromGeoLandItemData,
    val addition3: AddressFromGeoLandItemData,
    val addition4: AddressFromGeoLandItemData,
    val name: String
)

data class AddressFromGeoLandItemData(
    val type: String,
    val value: String
)

data class AddressFromGeoAreaData(
    val name: String
)