package com.motax.modutaxi.data.model.request

data class ReportRequest(
    val roomId: Long,
    val targetId: Long,
    val type: String,
    val content: String
)
