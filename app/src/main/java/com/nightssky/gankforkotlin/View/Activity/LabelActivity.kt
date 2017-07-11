package com.nightssky.gankforkotlin.View.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Adapter.LabelAdapter
import com.nightssky.gankforkotlin.View.Widget.DragHelper.OnStartDragListener
import com.nightssky.gankforkotlin.View.Widget.DragHelper.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.activity_label.*


class LabelActivity : AppCompatActivity(),OnStartDragListener {

    private var mItemTouchHelper:ItemTouchHelper?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_label)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        val adapter = LabelAdapter(this)
        recyclerView.adapter = adapter
        val callback = SimpleItemTouchHelperCallback(adapter)
         mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper?.startDrag(viewHolder)
    }

}
