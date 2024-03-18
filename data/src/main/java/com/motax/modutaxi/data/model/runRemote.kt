package com.motax.modutaxi.data.model

import com.google.gson.Gson
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.model.StatusCode
import retrofit2.Response

suspend fun <T> runRemote(block: suspend () -> Response<T>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it)
            } ?: BaseState.Error(StatusCode.EMPTY, "응답이 비어있습니다")
        } else {
            val errorData = Gson().fromJson(response.errorBody()?.string(), BaseState.Error::class.java)
            when(response.code()){
                401 -> BaseState.Error(StatusCode.ERROR_AUTH, errorData.message)
                404 -> BaseState.Error(StatusCode.ERROR_NONE, errorData.message)
                else -> BaseState.Error(StatusCode.ERROR, errorData.message)
            }
        }
    } catch (e: Exception) {
        BaseState.Error(StatusCode.EXCEPTION, e.message.toString())
    }
}