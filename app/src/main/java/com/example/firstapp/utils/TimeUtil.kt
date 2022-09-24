package com.example.firstapp.utils

import android.text.format.DateFormat
import android.text.format.DateUtils
import java.util.*

object TimeUtil {
    /**
     * 时间差
     * 发表时间
     */
    fun convert(time: Long): String {
        val second = (System.currentTimeMillis() - time) / 1000;    //计算秒
        val minute by lazy { second / 60 }
        val hour by lazy { minute / 60 }
        // 是否昨天
        val isYesterday by lazy {
            DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)
        }
        // 是本年
        val isThisYear by lazy {
            Calendar.getInstance().let {
                val dy = it.get(Calendar.YEAR)
                it.timeInMillis = time
                val di = it.get(Calendar.YEAR)
                dy == di
            }
        }
        return if (second < 60) {
            "刚刚"
        } else if (minute < 60) {
            "${minute}分钟前"
        } else if (hour < 24) {
            if (isYesterday) {
                "昨天 ${DateFormat.format("hh:mm", time)}"
            } else {
                "${hour}小时前"
            }
        } else if (isThisYear) {
            "${DateFormat.format("MM-dd", time)}"
        } else {
            "${DateFormat.format("yyyy-MM-dd", time)}"
        }
    }

}