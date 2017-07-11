package com.nightssky.gankforkotlin.View.Widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Created by user on 2017/6/16.
 */
class EmptyRecyclerView : RecyclerView {

    private var emptyView: View? = null

    private val observer = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Log.i(TAG, "onItemRangeInserted" + itemCount)
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle) {
    }

    private fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter.itemCount == 0
            emptyView!!.setVisibility(if (emptyViewVisible) View.VISIBLE else View.GONE)
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
        checkIfEmpty()
    }

    companion object {
        private val TAG = "hiwhitley"
    }
}