package com.example.firstapp.net.service

import com.example.firstapp.net.JokeResponse
import retrofit2.Call
import retrofit2.http.*

interface JokeService {

    @FormUrlEncoded
    @POST("/xhdq/common/joke/getJokes")
    fun getJokes(@Field("page") page:Int, @Field("pageSize") pageSize:Int): Call<JokeResponse>

}