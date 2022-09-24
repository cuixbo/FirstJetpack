package com.example.firstapp

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private fun log(logMessage: String) = println("[${Thread.currentThread().name}] $logMessage")

private fun myMethod(): Flow<Int> = (1..10).asFlow()

fun main() = runBlocking {
    myMethod().onStart { println("Flow onStart") }
        .onCompletion { cause -> println("Flow completed with $cause") }
        .collect { value ->
            delay(4000)
            println(value)
        }
    "https://api.weibo.com/oauth2/authorize?redirect_uri=https%3A%2F%2Fwww.baidu.com&response_type=code&client_id=2840624634"
    "https://weibo.com/ajax/feed/hottimeline?since_id=0&refresh=0&group_id=102803&containerid=102803&extparam=discover%7Cnew_feed&max_id=0&count=10"
    "https://weibo.com/ajax/feed/hottimeline?group_id=102803&containerid=102803&extparam=discover%257Cnew_feed&count=10&since_id=0&refresh=0&max_id=0"
    "https://weibo.com/ajax/feed/hottimeline?refresh=2&group_id=102803&containerid=102803&extparam=discover%7Cnew_feed&max_id=1&count=10"
    "https://weibo.com/ajax/feed/hottimeline?refresh=2&group_id=102803&containerid=102803&extparam=discover%7Cnew_feed&max_id=2&count=10"
    "https://weibo.com/ajax/feed/hottimeline?since_id=0&refresh=1&group_id=1028035088&containerid=102803_ctg1_5088_-_ctg1_5088&extparam=discover%7Cnew_feed&max_id=0&count=10"
}