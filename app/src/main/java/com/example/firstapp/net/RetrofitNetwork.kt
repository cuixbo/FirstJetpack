package com.example.firstapp.net

import android.util.Log
import com.example.firstapp.net.service.JokeService
import com.example.firstapp.net.service.WeiboService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object RetrofitNetwork {
    //获取最新笑话列表
    private val jokeService = ServiceCreator.create(JokeService::class.java)
    private val weiboService = ServiceCreator.create(WeiboService::class.java)

    suspend fun searchJokes(page: Int, pageSize: Int) = jokeService.getJokes(page, pageSize).await()

    suspend fun getHotTimelineFeeds(page: Int, pageSize: Int) =
        weiboService.getHotTimelineFeeds().await()

    private suspend fun <T> Call<T>.await(): T {
        Log.d("suspendCoroutine", request().toString())
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d("RetrofitNetwork", body.toString())
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d("onFailure", continuation.toString())
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}

