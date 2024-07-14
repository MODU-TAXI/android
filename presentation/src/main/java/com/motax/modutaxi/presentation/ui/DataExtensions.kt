package com.motax.modutaxi.presentation.ui

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt


fun AddressFromGeoItemData.toAddressString() =
    region.area1.name + " " + region.area2.name + " " + region.area3.name + " " + land.name +
            land.number1 + if (land.number2.isNotBlank()) "-" + land.number2 else ""


fun AddressFromGeoItemData.toBuildingName() = land.addition0.value

internal fun Int.formatNumberWithCommas(): String {
    return String.format("%,d", this)
}

internal fun Uri.toMultiPart(context: Context): MultipartBody.Part {
    val file = File(getRealPathFromUri(this, context) ?: "")
    val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", file.name, requestFile)
}

private fun getRealPathFromUri(uri: Uri, context: Context): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.let {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = it.getString(columnIndex)
        }
        it.close()
    }
    return filePath
}

fun String.toRoomTag(): RoomTag {
    return if (this == "ONLY_WOMAN") {
        RoomTag.ONLY_WOMAN
    } else if (this == "STUDENT_CERTIFICATION") {
        RoomTag.STUDENT_CERTIFICATION
    } else if (this == "MANNER") {
        RoomTag.MANNER
    } else {
        RoomTag.EMPTY
    }
}

fun getUTCTime(hour: Int, minute: Int): String {
    val seoulZoneId = ZoneId.of("Asia/Seoul")
    val currentSeoulDateTime = LocalDateTime.now(seoulZoneId)
        .withHour(hour)
        .withMinute(minute)
    val offset = ZoneOffset.from(currentSeoulDateTime.atZone(seoulZoneId))
    val localDateTimeWithOffset = currentSeoulDateTime.atOffset(offset)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    return localDateTimeWithOffset.format(formatter)
}

fun getTodayDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return currentDate.format(formatter)
}

fun getCurHour(): Int {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.hour
}

fun getCurMinute(): Int {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.minute
}

fun String.toChatSentTime() : String{
    val time = this.substring(0..18)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    inputFormat.parse(time)?.let{
        return outputFormat.format(it)
    } ?: run{
        return ""
    }
}

fun String.toDateTime() : String{
    val time = this.substring(0..18)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HHmm", Locale.getDefault())

    inputFormat.parse(time)?.let{
        return outputFormat.format(it)
    } ?: run{
        return ""
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371e3 // 지구 반지름 (미터)
    val phi1 = lat1 * (PI / 180)
    val phi2 = lat2 * (PI / 180)
    val deltaPhi = (lat2 - lat1) * (PI / 180)
    val deltaLambda = (lon2 - lon1) * (PI / 180)

    val a = sin(deltaPhi / 2).pow(2) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // 미터 단위의 거리
}

fun Double.toDistanceString(): String {
    return if (this < 1000) {
        "${this.toInt()} m"
    } else {
        "${String.format("%.2f", this / 1000)} km"
    }
}

fun Double.to8Round(): Double = round(this * 100000000) / 100000000

fun String.shortenAddress(): String {
    val regex = Regex("""(\S+동) (\S+로\d*번길)(\d+)""")
    val matchResult = regex.find(this)
    return if (matchResult != null) {
        val (dong, road, number) = matchResult.destructured
        "$dong $road $number"
    } else {
        this
    }
}
