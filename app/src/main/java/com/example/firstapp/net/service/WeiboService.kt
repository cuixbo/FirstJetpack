package com.example.firstapp.net.service

import com.example.firstapp.net.bean.FeedsListResponse
import retrofit2.http.*

interface WeiboService {

//    @GET("/ajax/feed/hottimeline")
//    fun getHotTimelineFeeds(
//        @Query("group_id") group_id: Int = 102803,
//        @Query("containerid") containerId: Int = 102803,
//        @Query("extparam") extParam: String = "discover|new_feed",
//        @Query("count") count: Int = 10,
//        @Query("since_id") since_id: Int = 0,
//        @Query("refresh") refresh: Int = 0,
//        @Query("max_id") max_id: Int = 0,
//    ): Call<FeedsListResponse>

    /**
     * 获取微博广场
     * Request URL: https://weibo.com/ajax/feed/hottimeline?refresh=2&group_id=102803&containerid=102803&extparam=discover%7Cnew_feed&max_id=2&count=10
     */
    @GET("/ajax/feed/hottimeline")
    suspend fun getHotTimelineFeeds(
        @Query("group_id") group_id: Int = 102803,
        @Query("containerid") containerId: Int = 102803,
        @Query("extparam") extParam: String = "discover|new_feed",
        @Query("count") count: Int = 10,
        @Query("since_id") since_id: Int = 0,
        @Query("refresh") refresh: Int = 0,
        @Query("max_id") max_id: Int = 0,
    ): FeedsListResponse


    /**
     * 获取单条微博
     * Request URL: https://weibo.com/ajax/statuses/show?id=M7G346ksP
     */
    @GET("/ajax/statuses/show")
    suspend fun getFeedById( @Query("id") id: String = ""): FeedsListResponse
}