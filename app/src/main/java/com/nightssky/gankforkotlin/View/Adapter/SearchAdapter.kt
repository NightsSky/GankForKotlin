package com.nightssky.gankforkotlin.View.Adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nightssky.gankforkotlin.Model.Entity.CategoryData
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Common.MyApplication

/**
 * Created by user on 2017/6/14.
 */
class SearchAdapter : BaseRecycleViewAdapter<CategoryData,BaseRecycleViewAdapter.BaseViewHolder> {
    constructor(layoutResId: Int) : super(layoutResId)



    override fun bindTheData(holder: BaseViewHolder, data: CategoryData, position: Int) {
        holder.setText(R.id.gank_title,data.desc)
        data.who?.let {

            holder.setText(R.id.who,data.who)
        }
        val image = holder.getView(R.id.image) as ImageView
        if (data.type.equals("福利")) {
            image.visibility = View.VISIBLE
            Glide.with(MyApplication.context())
                    .load(data.url)
                    .into(image)
        } else {
            image.visibility = View.GONE
        }
        holder.setText(R.id.type,data.type)
        holder.setText(R.id.time,data.publishedAt.substring(0,10))

    }



}