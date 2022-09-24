package com.example.firstapp.net

import com.google.gson.FieldNamingPolicy
import com.google.gson.FieldNamingStrategy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

//    "https://weibo.com/ajax/feed/hottimeline?refresh=2&group_id=102803&containerid=102803&extparam=discover%7Cnew_feed&max_id=2&count=10"

    private const val BASE_URL = "https://weibo.com/"

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
//            .addHeader("X-APISpace-Token", "mlpg6jaszov41hmjdk5qjm7t2h4pplw2")
//            .addHeader("Authorization-Type", "apikey")
            .build()
        val response = chain.proceed(request)
//        println("xbc")
        // println("xbc" + response.body()?.string())
        response
    }
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build();

    // Gson 支持下划线自动转驼峰
    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}