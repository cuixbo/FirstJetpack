package com.example.firstapp.net.bean

import com.google.gson.annotations.SerializedName

data class FeedsListBean(
    val ok: Int,
    val status: List<*>,
    @SerializedName("total_number") val totalNumber: Int,
    val since_id: Int,
    val max_id: Int
)
