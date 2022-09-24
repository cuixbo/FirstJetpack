package com.example.firstapp.net

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object Repository {


    fun searchJokes(page: Int, pageSize: Int) = fire(Dispatchers.IO) {
        val jokeInfoResponse = RetrofitNetwork.searchJokes(page, pageSize)
        Log.d("jokeInfoResponse", jokeInfoResponse.toString())
        if (jokeInfoResponse.statusCode == 0) {
            val joke = jokeInfoResponse.result.jokes
            Result.success(joke)
        } else {
            Result.failure(RuntimeException("joke response status is ${jokeInfoResponse.desc}"))
        }
    }

    suspend fun getJokes(page: Int, pageSize: Int): List<JokeResponse.Joke> {
        var jokes = listOf<JokeResponse.Joke>()
        withContext(Dispatchers.IO) {
            val jokeInfoResponse = RetrofitNetwork.searchJokes(page, pageSize)
            Log.d("jokeInfoResponse", jokeInfoResponse.toString())

            if (jokeInfoResponse.statusCode == 0) {
                jokes = jokeInfoResponse.result.jokes
            }
        }
        return jokes
    }

    suspend fun getHotTimelineFeeds(page: Int, pageSize: Int): List<FeedsListResponse.Feed> {
        var jokes = listOf<FeedsListResponse.Feed>()
        withContext(Dispatchers.IO) {
            val jokeInfoResponse = RetrofitNetwork.getHotTimelineFeeds(page, pageSize)
            Log.d("jokeInfoResponse", jokeInfoResponse.toString())

            if (jokeInfoResponse.ok == 1) {
                jokes = jokeInfoResponse.statuses
            }
        }
        return jokes
    }


    //使用suspend关键字，以表示所有传入的Lambda表达式中的代码也是有挂起函数上下文的
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<JokeResponse>(e)
            }
            emit(result as Result<T>)
        }

}