package com.example.firstapp.net

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.liveData
import com.example.firstapp.net.bean.FeedsListResponse
import com.example.firstapp.net.service.DownloadService
import com.example.firstapp.net.service.WeiboService
import com.example.firstapp.utils.copyTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import kotlin.coroutines.CoroutineContext


object Repository {

    /**
     * 普通方式
     * LiveData方式 和 不使用LiveData方式
     * Flow 方式
     * 协程的使用
     *
     * Retrofit中，对于挂起函数，自动处理了网络请求的线程切换，执行完会切换回原来的线程
     * 假设请求也是在工作线程发出的，使用LiveData的postValue会将数据变更又切回到主线程；
     * 如果请求是主线程发出的，LiveData的setValue也是在主线程的；
     */

    val weiboService = RetrofitClient.create(WeiboService::class.java)
    val downloadService = RetrofitClient.create(DownloadService::class.java)

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

//    /**
//     * 普通方式，直接返回List<T>
//     */
//    suspend fun getHotTimelineFeeds(page: Int, pageSize: Int): List<FeedsListResponse.Feed> {
//        var jokes = listOf<FeedsListResponse.Feed>()
//        withContext(Dispatchers.IO) {
//            val jokeInfoResponse = RetrofitNetwork.getHotTimelineFeeds(page, pageSize)
//            Log.d("jokeInfoResponse", jokeInfoResponse.toString())
//
//            if (jokeInfoResponse.ok == 1) {
//                jokes = jokeInfoResponse.statuses
//            }
//        }
//        return jokes
//    }

    /**
     * 参考封装链接
     * https://www.cnblogs.com/sw-code/p/15591713.html
     */
    suspend fun getHotTimelineFeeds(page: Int = 0): FeedsListResponse {
        return weiboService.getHotTimelineFeeds()
    }

    @WorkerThread
    fun downloadImage(url: String): Flow<Int> = flow<Int> {
        val response = downloadService.downloadImage(url).execute()
        if (response.isSuccessful) {
            val fileName = url.substringAfterLast("/")
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            var total: Long = 0
            // 这种方法可以跑满速度，进度很快
            response.body()!!
                .also { total = it.contentLength() }
                .byteStream()
                .use {
                    file.outputStream().use { fos ->
                        it.copyTo(fos) {
                            val progress = (100 * it / total).toInt()
//                            if (progress % 5 == 0) delay(2)
                            emit(progress)
                        }
                    }
                }

            // 下面的方法可以明显的看出加载速度来，进度慢一点点
//            var bytesCopied: Long = 0
//            response.body()?.also {
//                total = it.contentLength()
//            }?.byteStream()!!.buffered().use { bis ->
//                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//                var bytes = bis.read(buffer)
//                while (bytes >= 0) {
//                    bytesCopied += bytes
//                    file.appendBytes(buffer)
//                    val percent = (100 * bytesCopied / total).toInt()
//                    emit(percent)
//                    bytes = bis.read(buffer)
//                }
//            }
        }
    }


    // 使用suspend关键字，以表示所有传入的Lambda表达式中的代码也是有挂起函数上下文的
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