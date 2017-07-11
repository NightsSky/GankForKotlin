package com.nightssky.gankforkotlin.View.Adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Common.MyApplication
import com.nightssky.gankforkotlin.View.Widget.ScaleImageView


/**
 * Created by user on 2017/6/14.
 */
class MeiziAdapter : BaseRecycleViewAdapter<DetailsData,BaseRecycleViewAdapter.BaseViewHolder> {
    constructor(layoutResId: Int) : super(layoutResId)

    override fun bindTheData(holder: BaseViewHolder, data: DetailsData, position: Int) {

        val imageView = holder.getView(R.id.img_girls) as ScaleImageView

        val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (data.width!=null||data.height!=null) {
            imageView.setInitSize(data.width,data.height)
        }
        Glide.with(MyApplication.context())
                .load(data.url)
                .apply(options)
                .into(imageView)

    }




}