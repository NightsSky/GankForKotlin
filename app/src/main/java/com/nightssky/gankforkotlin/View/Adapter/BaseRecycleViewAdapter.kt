package com.nightssky.gankforkotlin.View.Adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * Created by user on 2017/3/31.
 */
//D(实体类)和VH分别代表了的 数据源、ViewHold
abstract class BaseRecycleViewAdapter<D, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>, View.OnClickListener {
    private var mList: MutableList<D>? = null
    private var layoutResId: Int = 0
    private var clickListener: onItemClickListener? = null

    constructor(list: MutableList<D>, layoutResId: Int) {
        mList = list

        if (layoutResId != 0) {
            this.layoutResId = layoutResId
        } else {
            throw NullPointerException("请设置Item资源id")
        }
    }

    constructor(layoutResId: Int) {
        mList = ArrayList<D>()
        if (layoutResId != 0) {
            this.layoutResId = layoutResId
        } else {
            throw NullPointerException("请设置Item资源id")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return BaseViewHolder(view) as VH
    }

    //添加数据
    fun addData(list: List<D>) {
        val size = this.mList!!.size
        this.mList!!.addAll(list)
        notifyItemInserted(size + 1)
    }

    //更新数据
    fun setData(list: List<D>) {
        if (mList!!.size > 0) {
            mList!!.clear()
        }
        mList!!.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener(this)
        holder.itemView.tag = position
        bindTheData(holder, mList!![position], position)
    }

    /**
     * 绑定数据

     * @param holder 视图管理者
     * *
     * @param data   数据源
     */
    protected abstract fun bindTheData(holder: VH, data: D, position: Int)

//    override fun getItemCount(): Int {
//        return mList!!.size
//    }
    override fun getItemCount()= mList!!.size

    override fun onClick(v: View) {
        //点击回调
        if (clickListener != null) {
            clickListener!!.onItemClick(v.tag as Int, v)
        }
    }

    fun getItem(position: Int): D? {
        if (position < mList!!.size) {
            return mList!![position]
        }
        return null
    }

    /**
     * 自定义ViewHolder
     */
     class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val views: SparseArray<View> = SparseArray<View>()

        fun <T : View> findViewById(resId: Int): T {
            var view: View? = views.get(resId)
            if (view == null) {
                view = itemView.findViewById(resId)
                views.put(resId, view)
            }
            return view as T
        }

        /**
         * 设置文本资源

         * @param viewId view id
         * *
         * @param s      字符
         */
        fun setText(viewId: Int, s: CharSequence): TextView {
            val view = findViewById<TextView>(viewId)
            view.text = s
            return view
        }

//        /**
//         * 设置图片
//         * @param viewId
//         * *
//         * @param url
//         * *
//         * @return
//         */
//        fun setImg(viewId: Int, url: CharSequence): ImageView {
//            val view = findViewById<ImageView>(viewId)
//            //            Glide.with(MyApplication.getInstance())
//            //                    .load(url)
//            //                    .placeholder(R.mipmap.image_loading)
//            //                    .crossFade(100)
//            //                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//            //                    .centerCrop()
//            //                    .into(view);
//            return view
//        }

        fun getView(viewId: Int): View {
            val view = findViewById<View>(viewId)
            return view
        }
    }

    /**
     * 设置点击监听

     * @param clickListener 监听器
     */
    fun setItemClickListener(clickListener: onItemClickListener) {
        this.clickListener = clickListener
    }

    interface onItemClickListener {
        fun onItemClick(position: Int, v: View)
    }

    interface onItemLongClickListener {
        fun onItemLonClick(position: Int, v: View): Boolean
    }
}
