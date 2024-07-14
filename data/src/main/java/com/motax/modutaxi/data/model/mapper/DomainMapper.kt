package com.motax.modutaxi.data.model.mapper

import android.text.Html
import com.motax.modutaxi.data.model.response.AccountResponseItem
import com.motax.modutaxi.data.model.response.AccountResponse
import com.motax.modutaxi.data.model.response.AddressFromGeoResponse
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.CertificateResponse
import com.motax.modutaxi.data.model.response.ChatMessageResponse
import com.motax.modutaxi.data.model.response.ChatInfoResponse
import com.motax.modutaxi.data.model.response.GetNearSpotResponse
import com.motax.modutaxi.data.model.response.GetSpotListResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.MemberInfo
import com.motax.modutaxi.data.model.response.MemberDetailResponse
import com.motax.modutaxi.data.model.response.MemberProfileResponse
import com.motax.modutaxi.data.model.response.NotificationCountResponse
import com.motax.modutaxi.data.model.response.NotificationResponse
import com.motax.modutaxi.data.model.response.NotificationResponseItem
import com.motax.modutaxi.data.model.response.PaymentMemberListResponse
import com.motax.modutaxi.data.model.response.SearchResultItem
import com.motax.modutaxi.data.model.response.SearchResultListResponse
import com.motax.modutaxi.data.model.response.TaxiPotDetailResponse
import com.motax.modutaxi.data.model.response.TaxiPotItem
import com.motax.modutaxi.data.model.response.TaxiPotListRadiusResponse
import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import com.motax.modutaxi.data.model.response.TaxiPotMemberItem
import com.motax.modutaxi.data.model.response.TaxiPotParticipants
import com.motax.modutaxi.data.model.response.TaxiPotPreviewResponse
import com.motax.modutaxi.data.model.response.TaxiPotWaitingMembers
import com.motax.modutaxi.data.model.response.TokenResponse
import com.motax.modutaxi.data.model.response.UpdateMemberResponse
import com.motax.modutaxi.data.model.response.UploadImageResponse
import com.motax.modutaxi.data.model.response.UsageDetailResponse
import com.motax.modutaxi.data.model.response.UsageHistoryItemResponse
import com.motax.modutaxi.data.model.response.UsageHistoryResponse
import com.motax.modutaxi.data.model.response.UsageParticipantResponse
import com.motax.modutaxi.domain.model.AccountData
import com.motax.modutaxi.domain.model.AccountDataItem
import com.motax.modutaxi.domain.model.AddressFromGeoAreaData
import com.motax.modutaxi.domain.model.AddressFromGeoCodeData
import com.motax.modutaxi.domain.model.AddressFromGeoData
import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import com.motax.modutaxi.domain.model.AddressFromGeoLandData
import com.motax.modutaxi.domain.model.AddressFromGeoLandItemData
import com.motax.modutaxi.domain.model.AddressFromGeoRegionData
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.CertificateData
import com.motax.modutaxi.domain.model.ChatMessageData
import com.motax.modutaxi.domain.model.ChatMessageItemData
import com.motax.modutaxi.domain.model.ChatInfoData
import com.motax.modutaxi.domain.model.CoordinateData
import com.motax.modutaxi.domain.model.CoordinateReferenceSystemData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.model.MemberDetailData
import com.motax.modutaxi.domain.model.MemberInfoData
import com.motax.modutaxi.domain.model.MemberProfileData
import com.motax.modutaxi.domain.model.MonthlyUsageHistoryData
import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.domain.model.NotificationCountData
import com.motax.modutaxi.domain.model.NotificationData
import com.motax.modutaxi.domain.model.NotificationDataItem
import com.motax.modutaxi.domain.model.PathData
import com.motax.modutaxi.domain.model.PaymentMemberListData
import com.motax.modutaxi.domain.model.SearchResultData
import com.motax.modutaxi.domain.model.SearchResultListData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.SpotListItemData
import com.motax.modutaxi.domain.model.TaxiPotListItemData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusItemData
import com.motax.modutaxi.domain.model.TaxiPotMemberData
import com.motax.modutaxi.domain.model.TaxiPotParticipantsData
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.model.TaxiPotWaitingMembersData
import com.motax.modutaxi.domain.model.TokenData
import com.motax.modutaxi.domain.model.UpdateMemberData
import com.motax.modutaxi.domain.model.UploadImageData
import com.motax.modutaxi.domain.model.UsageDetailData
import com.motax.modutaxi.domain.model.UsageHistoryDataItem
import com.motax.modutaxi.domain.model.UsageParticipantData

fun TokenResponse.toDomain() = TokenData(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun MemberCheckResponse.toDomain() = MemberCheckData(
    key = key ?: "",
)

fun CertificateResponse.toDomain() = CertificateData(
    isConfirm = isConfirm
)

fun TaxiPotItem.toDomain() = TaxiPotListItemData(
    roomId = roomId,
    spotId = spotId,
    arrivalTime = arrivalTime,
    arrivalName = arrivalName,
    roomTagBitMaskList = roomTagBitMaskList,
    departureName = departureName,
    departureTime = departureTime,
    wishHeadcount = wishHeadcount,
    currentHeadcount = currentHeadcount,
    durationMinutes = durationMinutes,
    expectedChargePerPerson = expectedChargePerPerson,
    expectedCharge = expectedCharge,
    departureLatitude = departureLatitude,
    departureLongitude = departureLongitude
)

fun TaxiPotListResponse.toDomain() = TaxiPotListData(
    rooms = rooms.map { it.toDomain() }
)

fun SearchResultItem.toDomain() = SearchResultData(
    title = Html.fromHtml(title).toString(),
    roadAddress = roadAddress,
    mapx = mapx,
    mapy = mapy
)

fun SearchResultListResponse.toDomain() = SearchResultListData(
    results = items.map { it.toDomain() }
)

fun AddressFromGeoResponse.toDomain() = AddressFromGeoData(
    results = results.map {
        AddressFromGeoItemData(
            name = it.name,
            code = AddressFromGeoCodeData(
                id = it.code.id,
                mappingId = it.code.mappingId,
                type = it.code.type
            ),
            region = AddressFromGeoRegionData(
                area0 = AddressFromGeoAreaData(name = it.region.area0.name),
                area1 = AddressFromGeoAreaData(name = it.region.area1.name),
                area2 = AddressFromGeoAreaData(name = it.region.area2.name),
                area3 = AddressFromGeoAreaData(name = it.region.area3.name),
                area4 = AddressFromGeoAreaData(name = it.region.area4.name),
            ),
            land = AddressFromGeoLandData(
                type = it.land.type,
                number1 = it.land.number1,
                number2 = it.land.number2,
                addition0 = AddressFromGeoLandItemData(
                    type = it.land.addition0.type,
                    value = it.land.addition0.value
                ),
                addition1 = AddressFromGeoLandItemData(
                    type = it.land.addition1.type,
                    value = it.land.addition1.value
                ),
                addition2 = AddressFromGeoLandItemData(
                    type = it.land.addition2.type,
                    value = it.land.addition2.value
                ),
                addition3 = AddressFromGeoLandItemData(
                    type = it.land.addition3.type,
                    value = it.land.addition3.value
                ),
                addition4 = AddressFromGeoLandItemData(
                    type = it.land.addition4.type,
                    value = it.land.addition4.value
                ),
                name = it.land.name
            )
        )
    }
)

fun GetNearSpotResponse.toDomain() = NearSpotData(
    maxLongitude = maxLongitude,
    maxLatitude = maxLatitude,
    minLongitude = minLongitude,
    minLatitude = minLatitude,
    spots = spots.map {
        NearSpotItemData(
            it.id,
            it.name,
            it.address,
            it.longitude,
            it.latitude
        )
    }
)

fun GetSpotListResponse.toDomain() = SpotListData(spots.map {
    SpotListItemData(
        it.id,
        it.name,
        it.address,
        it.longitude,
        it.latitude,
        it.distance,
        it.liked
    )
})

fun AuthResponse.toDomain() = AuthData(
    tokenData = tokenResponse.toDomain(),
    memberInfoData = memberInfoResponse.toDomain()
)

fun MemberInfo.toDomain() = MemberInfoData(
    id = id,
    name = name,
    nickname = nickname ?: "",
    gender = gender,
    phoneNumber = phoneNumber,
    email = email ?: "",
    matchingCount = matchingCount,
    blocked = blocked,
    imageUrl = imageUrl
)

fun TaxiPotDetailResponse.toDomain() = TaxiPotDetailData(
    arrivalLatitude = arrivalLatitude,
    arrivalLongitude = arrivalLongitude,
    arrivalName = arrivalName,
    arrivalTime = arrivalTime,
    currentHeadcount = currentHeadcount,
    departureDairyDate = departureDairyDate,
    departureLatitude = departureLatitude,
    departureLongitude = departureLongitude,
    departureName = departureName,
    departureTime = departureTime,
    durationMinutes = durationMinutes,
    expectedCharge = expectedCharge,
    expectedChargePerPerson = expectedChargePerPerson,
    managerId = managerId,
    myRoom = myRoom,
    participate = participate,
    path = PathData(
        coordinateReferenceSystem = CoordinateReferenceSystemData(
            path.coordinateReferenceSystem?.type ?: ""
        ),
        coordinates = path.coordinates.map {
            CoordinateData(it.values)
        },
        type = path.type
    ),
    profileImageUrl = profileImageUrl,
    roomId = roomId,
    roomTagBitMaskList = roomTagBitMaskList,
    spotId = spotId,
    wishHeadcount = wishHeadcount,
    waiting = waiting,
    minLatitude = minLatitude,
    minLongitude = minLongitude,
    maxLatitude = maxLatitude,
    maxLongitude = maxLongitude
)

fun TaxiPotListRadiusResponse.toDomain() = TaxiPotListRadiusData(
    rooms = rooms.map {
        TaxiPotListRadiusItemData(
            id = it.id,
            departureLongitude = it.departureLongitude,
            departureLatitude = it.departureLatitude,
            spotName = it.spotName
        )
    }
)

fun TaxiPotPreviewResponse.toDomain() = TaxiPotPreviewData(
    roomId = roomId,
    departureTime = departureTime,
    departureName = departureName,
    arrivalName = arrivalName,
    roomStatus = roomStatus,
    currentHeadcount = currentHeadcount,
    wishHeadcount = wishHeadcount,
    expectedChargePerPerson = expectedChargePerPerson,
    expectedCharge = expectedCharge
)

fun TaxiPotMemberItem.toDomain() = TaxiPotMemberData(
    memberId = memberId,
    nickname = nickname,
    imageUrl = imageUrl,
    matchingCount = matchingCount,
    thisIsMe = thisIsMe,
    certified = certified
)

fun TaxiPotParticipants.toDomain() = TaxiPotParticipantsData(
    inList = inList.map { it.toDomain() }
)

fun TaxiPotWaitingMembers.toDomain() = TaxiPotWaitingMembersData(
    waitingList = waitingList.map { it.toDomain() }
)

fun ChatMessageResponse.toDomain() = ChatMessageData(
    messages = messages.map { data ->
        ChatMessageItemData(
            roomId = data.roomId,
            messageType = data.messageType,
            content = data.content,
            sender = data.sender,
            memberId = data.memberId,
            dateTime = data.dateTime,
            imageUrl = data.imageUrl
        )
    }
)

fun ChatInfoResponse.toDomain() = ChatInfoData(
    roomId = roomId,
    memberId = memberId
)

fun MemberProfileResponse.toDomain() = MemberProfileData(
    id = id,
    nickname = nickname,
    matchingCount = matchingCount,
    imageUrl = imageUrl,
    certified = certified
)

fun MemberDetailResponse.toDomain() = MemberDetailData(
    id = this.id,
    nickname = this.nickname,
    matchingCount = this.matchingCount,
    imageUrl = this.imageUrl,
    certified = this.certified
)

fun UpdateMemberResponse.toDomain() = UpdateMemberData(
    name = name,
    gender = gender,
    phoneNumber = phoneNumber,
    imageUrl = imageUrl
)

fun UploadImageResponse.toDomain() = UploadImageData(
    imageUrl = imageUrl,
    fileName = fileName
)

fun UsageHistoryResponse.toDomain() = MonthlyUsageHistoryData(
    year = this.year,
    month = this.month,
    totalCharge = this.accumulateTotalCharge,
    accumulatePortionCharge = this.accumulatePortionCharge,
    historyList = this.historySimpleListResponse.map { it.toDomain() }
)

fun UsageHistoryItemResponse.toDomain() = UsageHistoryDataItem(
    historyId = this.historyId,
    departureTime = this.departureTime,
    departureName = this.departureName,
    arrivalName = this.arrivalName,
    portionCharge = this.portionCharge
)

fun UsageDetailResponse.toDomain(): UsageDetailData {
    return UsageDetailData(
        managerId = managerId,
        historyId = historyId,
        roomId = roomId,
        departureTime = departureTime,
        departureName = departureName,
        arrivalName = arrivalName,
        totalCharge = totalCharge,
        portionCharge = portionCharge,
        paymentMemberListData = paymentMemberListResponse.toDomain()
    )
}

fun PaymentMemberListResponse.toDomain(): PaymentMemberListData {
    return PaymentMemberListData(
        participantList = participantList.map { it.toDomain() }
    )
}

fun UsageParticipantResponse.toDomain(): UsageParticipantData {
    return UsageParticipantData(
        id = id,
        nickName = nickName,
        name = name,
        imageUrl = imageUrl ?: "",
        status = status,
        me = me,
        portionCharge = portionCharge
    )
}

fun AccountResponse.toDomain(): AccountData {
    return AccountData(
        accounts = this.accounts.map { it.toDomain() }
    )
}

fun AccountResponseItem.toDomain(): AccountDataItem {
    return AccountDataItem(
        id = id,
        accountNumber = accountNumber,
        bank = bank
    )
}

fun NotificationResponse.toDomain(): NotificationData {
    return NotificationData(
        result = this.result.map { it.toDomain() }
    )
}

fun NotificationResponseItem.toDomain(): NotificationDataItem {
    return NotificationDataItem(
        type = type,
        message = message,
        resourceId = resourceId,
        dateTime = dateTime,
        checked = checked
    )
}

fun NotificationCountResponse.toDomain(): NotificationCountData {
    return NotificationCountData(
        counts = counts
    )
}