package com.nightssky.gankforkotlin.View.Adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Common.MyApplication
import java.text.SimpleDateFormat

/**
 * Created by user on 2017/6/14.
 */
class GankAdapter : BaseRecycleViewAdapter<DetailsData,BaseRecycleViewAdapter.BaseViewHolder> {
    constructor(layoutResId: Int) : super(layoutResId)



    override fun bindTheData(holder: BaseViewHolder, data: DetailsData, position: Int) {
        holder.setText(R.id.gank_title, data.desc)
        data.who?.let {

            holder.setText(R.id.who, data.who)
        }
        val image = holder.getView(R.id.image) as ImageView

        if (data.type.equals("福利")) {
            image.visibility = View.VISIBLE
            Glide.with(MyApplication.context())
                    .load(data.url)
                    .into(image)
        } else if (data.images == null) {
            image.visibility = View.GONE
        } else {
            image.visibility = View.VISIBLE

            Glide.with(MyApplication.context())
                    .load(data.images[0])
                    .into(image)
        }


        holder.setText(R.id.type, data.type)
        // 初始化时设置 日期和时间模式
        var sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        // 修改日期和时间模式
        sdf.applyPattern("yyyy-MM-dd ")
        var dateStr = sdf.format(data.publishedAt);
        holder.setText(R.id.time, dateStr)

    }



}