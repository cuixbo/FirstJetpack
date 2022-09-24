package com.example.firstapp.net

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    val desc: String,
    val result: Result,
    @SerializedName("statusCode") val statusCode: Int
) {
    data class Result(val jokes: List<Joke>)
    data class Joke(
        val content: String,
        val id: Int,
        val updateTime: String
    )

}
