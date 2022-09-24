package com.example.firstapp.net

import retrofit2.Call
import retrofit2.http.*

interface WeiboService {

    @GET("/ajax/feed/hottimeline")
    fun getHotTimelineFeeds(
        @Query("group_id") group_id: Int = 102803,
        @Query("containerid") containerId: Int = 102803,
        @Query("extparam") extParam: String = "discover|new_feed",
        @Query("count") count: Int = 10,
        @Query("since_id") since_id: Int = 0,
        @Query("refresh") refresh: Int = 0,
        @Query("max_id") max_id: Int = 0,
    ): Call<FeedsListResponse>

}