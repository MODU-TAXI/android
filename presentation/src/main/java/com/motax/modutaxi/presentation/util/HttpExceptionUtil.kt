package com.motax.modutaxi.presentation.util

import com.google.gson.Gson
import com.google.gson.JsonObject


fun extractMessageFromErrorBody(errorBody: String?): String {
    return if (errorBody.isNullOrEmpty()) {
        "응답이 없습니다."
    } else {
        try {
            // JSON을 JsonObject로 파싱
            val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)

            // "message" 필드가 존재하면 그 값을 반환
            if (jsonObject.has("message")) {
                jsonObject.get("message").asString
            } else if (jsonObject.has("errorCode")) {
                // "errorCode"가 있는 경우에도 "message"를 찾는다.
                jsonObject.get("message")?.asString ?: "알 수 없는 오류입니다."
            } else {
                "알 수 없는 오류입니다."
            }
        } catch (e: Exception) {
            "파싱 오류: ${e.message}"
        }
    }
}