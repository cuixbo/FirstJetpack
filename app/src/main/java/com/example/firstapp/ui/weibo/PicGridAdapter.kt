package com.example.firstapp.ui.weibo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.firstapp.R

class PicGridAdapter(val context: Context, private val data: List<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        println("cxb - getView")
        val holder: ViewHolder
        var view: View? = null
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_pic, null)
            holder = ViewHolder()
            holder.ivPic = view.findViewById(R.id.iv_pic)
            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
//        holder.ivPic
        val url = "https://wx1.sinaimg.cn/orj480/0084N5IFgy1h6eh5xys3hj317g0ohabs.jpg"
        Glide.with(context)
            .load(getItem(position))
            .override(Target.SIZE_ORIGINAL)
            .into(holder.ivPic!!);
        return convertView ?: view!!
    }

    class ViewHolder {
        var ivPic: ImageView? = null
    }
}