package com.example.firstapp.net.service

import com.example.firstapp.net.bean.FeedsListResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface DownloadService {


    /**
     * 获取微博广场
     * Request URL:https://b.zol-img.com.cn/desk/bizhi/image/1/5120x2880/1635925199470.jpg
     */
    @GET
    @Streaming
    fun downloadImage(@Url fileUrl: String): Call<ResponseBody>


}