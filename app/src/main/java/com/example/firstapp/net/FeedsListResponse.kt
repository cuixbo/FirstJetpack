package com.example.firstapp.net

import com.example.firstapp.utils.TimeUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class FeedsListResponse(
    val ok: Int,
    val statuses: List<Feed>,
    @SerializedName("total_number") val totalNumber: Int,
    val since_id: String,
    val max_id: Int
) {
    data class Feed(
        val text_raw: String,
        val text: String,
        val created_at: String,
        val source: String,
        val pic_ids: List<String>?,
        val pic_num: Int,
        val pic_infos: Map<String, PicSpec>?,
        val page_info: PageInfo?,
        val user: User,

        // pic_ids , pic_infos , pic_num
        // page_info
        // user

    ) {

        val created_at_date: String
            get() {
                // todo 这里能否使用懒加载
                println("created_at:$created_at")
                val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE)

                val date = Date(created_at)

                return TimeUtil.convert(date.time)
            }


    }

    data class User(
        val id: Long,
        val screen_name: String,
        val avatar_large: String,
    )

    data class PicSpec(
        val thumbnail: PicSpecInfo?,
        val large: PicSpecInfo?,
        val original: PicSpecInfo?,
    )

    data class PicSpecInfo(
        val url: String,
        val width: Int,
        val height: Int
    )

    data class PageInfo(
        val type: Int,
        val page_id: String,
        val pic_info: PicInfo?
    )

    data class PicInfo(
        val pic_big: PicSpecInfo?,
        val pic_small: PicSpecInfo?,
        val pic_middle: PicSpecInfo?,
    )

}
