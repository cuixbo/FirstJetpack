package com.example.firstapp.ui.weibo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.firstapp.databinding.ItemFeedBinding
import com.example.firstapp.net.bean.FeedsListResponse.Feed

class FeedAdapter(private val data: List<Feed>) : Adapter<FeedViewHolder>() {
    private val options = RequestOptions().override(Target.SIZE_ORIGINAL)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FeedViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = data[position]
        feed.let {
            holder.binding.apply {
                tvTitle.text = it.user.screen_name
                tvCreateAt.text = it.created_at_date
                tvSource.text = it.source
                tvText.text = it.text_raw
                Glide.with(holder.itemView)
                    .load(it.user.avatar_large)
                    .transform(CircleCrop())
                    .into(ivAvatar)
                // 清除图片、九宫格，否则holder复用时会混乱
                Glide.with(holder.itemView).clear(ivPic);
                gridView.adapter = null
                if (it.pic_num > 0 && !it.pic_ids.isNullOrEmpty() && !it.pic_infos.isNullOrEmpty()) {
                    // 有图片
                    if (it.pic_num > 1) {
                        val data = it.pic_infos.values.take(9).map {
                            it.large!!.url
                        }
                        gridView.adapter =
                            PicGridAdapter(holder.itemView.context, data)
                    } else {
                        val picId = it.pic_ids[0]
                        val picSpec = it.pic_infos[picId]

                        Glide.with(holder.itemView.context)
                            .load(picSpec?.original?.url.https())
                            .apply(options)
                            .into(ivPic);
                    }
//                    tvCreateAt.text = "${tvCreateAt.text} - pic${it.pic_num}"
                } else {
                    // 没有图片，有视频
                    it.page_info?.pic_info?.pic_middle?.let {
                        Glide.with(holder.itemView.context)
                            .load(it.url.https())
                            .apply(options)
                            .into(ivPic);
                        tvCreateAt.text = "${tvCreateAt.text} - video"
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun httpsUrl(url: String?): String? {
        return when {
            url == null -> url
            url.startsWith("http://") -> url.replace("http://", "https://")
            else -> url
        }
    }

    private fun String?.https(): String? {
        return this?.replace("http://", "https://")
    }
}

class FeedViewHolder(val binding: ItemFeedBinding) : ViewHolder(binding.root)