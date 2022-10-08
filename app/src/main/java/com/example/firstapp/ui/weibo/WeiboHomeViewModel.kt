package com.example.firstapp.ui.weibo

import androidx.lifecycle.*
import com.example.firstapp.net.bean.FeedsListResponse
import com.example.firstapp.net.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class WeiboHomeViewModel : ViewModel() {

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: MutableLiveData<Boolean> = _refresh

    val feeds = MutableLiveData<List<FeedsListResponse.Feed>>()

    val stateFeeds = MutableLiveData<State<List<FeedsListResponse.Feed>>>()

    val refreshTrigger: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    /**
     * 通过 Transformations.switchMap
     * feedsList数据源可以感知refreshTrigger数据源的变化
     * 场景：如果需要多次调用接口的场景，可以使用这种方式：
     * 1。触发refreshTrigger，就会触发feedsList的switchMap执行，获取新的数据
     * 2。这样View层liveData始终是同一个feedsList，它的observe不会变，feedsList的LiveData值变了而已；
     */
    val feedsList: LiveData<State<List<FeedsListResponse.Feed>>> =
        Transformations.switchMap(refreshTrigger) {
            println("cxb feedsList")
            getFeed2()
        }.map {
            it
        }


    /**
     * 面向State的LiveDataScope方式
     */
    val feeds2: LiveData<State<List<FeedsListResponse.Feed>>> = liveData {
        println("cxb:feeds2")
        emit(State.Loading)
        try {
            val res = Repository.getHotTimelineFeeds()
            if (res.ok == 1) {
                emit(State.Success(res.statuses))
            } else {
                emit(State.Error(Error("error:ok=${res.ok}")))
            }
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    /**
     * fun方法一次性的LiveData可用于多次订阅,会存在内存泄露吗？每次都创建了liveData并没有移除观察
     */
    fun getFeed2(): LiveData<State<List<FeedsListResponse.Feed>>> = liveData {
        println("cxb:getFeed2")
        emit(State.Loading)
        try {
            val res = Repository.getHotTimelineFeeds()
            if (res.ok == 1) {
                emit(State.Success(res.statuses))
            } else {
                emit(State.Error(Error("error:ok=${res.ok}")))
            }
        } catch (e: Exception) {
            emit(State.Error(e))
        }

    }

    /**
     * 面向State的flow方式转LiveData
     * TODO
     */
    val feeds3: LiveData<State<List<FeedsListResponse.Feed>>> = apicall {
        Repository.getHotTimelineFeeds().statuses
    }.asLiveData()

    fun setRefresh(refresh: Boolean) {
        _refresh.value = refresh
    }

    /**
     * viewModelScope,手动更新LiveData
     */
    fun getFeeds() = viewModelScope.launch {
        val res = Repository.getHotTimelineFeeds()
        if (res.ok == 1) {
            feeds.value = res.statuses
        } else {
            println("error:ok=${res.ok}")
        }
    }

    /**
     * viewModelScope,手动更新LiveData
     */
    fun getFeedsNew() = viewModelScope.launch {
        // 这里还可以再做一层封装，没必要放到vm中
        stateFeeds.value = State.Loading
        try {
            val res = Repository.getHotTimelineFeeds()
            // ok封装到base
            if (res.ok == 1) {
                stateFeeds.value = State.Success(res.statuses)
            } else {
                // api异常需要封装
                stateFeeds.value = State.Error(Error("error:ok=${res.ok}"))
            }

        } catch (e: Exception) {
            stateFeeds.value = State.Error(e)
        }
    }

    /**
     * http://events.jianshu.io/p/48caa46704e8
     * LiveDataScope, viewModelScope和lifecycleScope会自动处理自身的生命周期，
     * 在生命周期结束时会自动取消没有执行完成的协程任务
     */

    /**
     * 面向State的flow方式
     */
    fun <T> apicall(call: suspend () -> T): Flow<State<T>> =
        flow {
            emit(State.Loading)
            emit(State.Success(data = call.invoke()))
        }.catch { error ->
            emit(State.Error(error))
        }.flowOn(Dispatchers.IO)

}

//class HomeVM : ViewModel() {
//    private val refreshTrigger = MutableLiveData<*>()
//    private val api = WanApi.get()
//    private val bannerList: LiveData<*> = Transformations.switchMap(refreshTrigger)
//    {
//        //当refreshTrigger的值被设置时，
//
//        api.bannerList()
//    }
//
//    val banners: LiveData<*> = Transformations.map(bannerList)  { it.data ?: ArrayList() }
//    fun loadData() {
//        refreshTrigger.value = true
//    }
//}
//}

