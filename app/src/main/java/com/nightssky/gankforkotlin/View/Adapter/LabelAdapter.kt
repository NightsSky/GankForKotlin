package com.nightssky.gankforkotlin.View.Adapter

import android.graphics.Color
import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.nightssky.gankforkotlin.Model.*
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Widget.DragHelper.ItemTouchHelperAdapter
import com.nightssky.gankforkotlin.View.Widget.DragHelper.ItemTouchHelperViewHolder
import com.nightssky.gankforkotlin.View.Widget.DragHelper.OnStartDragListener

/**
 * Created by 追寻何意？又往何处 on 2017/6/15.
 */
class LabelAdapter : RecyclerView.Adapter<LabelAdapter.ItemViewHolder>,ItemTouchHelperAdapter {

    private var mDragStartListener: OnStartDragListener? = null

    constructor(mDragStartListener: OnStartDragListener){
        this.mDragStartListener = mDragStartListener

    }

    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): ItemViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_label_recycle_view, parent, false)
        val itemViewHolder = ItemViewHolder(view)
        return itemViewHolder
    }

    override fun getItemCount(): Int {

        return Constants.TYPELIST.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        holder.textView.text = Constants.TYPELIST.getAllList()[position]
        holder.switch_btn.isChecked  = Constants.TYPELIST.getShow(position)
        holder.label.setOnTouchListener(View.OnTouchListener { _, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener?.onStartDrag(holder)
            }
            false
        })
        holder.switch_btn.setOnCheckedChangeListener { _, isChecked ->
            Constants.TABISCHANGED = true
            Constants.TYPELIST.setShow(position,isChecked)
        }
    }






    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
//        Collections.swap(data, fromPosition, toPosition)
        Constants.TYPELIST?.swap(fromPosition, toPosition)
        Constants.TABISCHANGED = true
        notifyItemMoved(fromPosition, toPosition)
        return true

    }

    override fun onItemDismiss(position: Int) {

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {

        val textView: TextView
        val switch_btn: Switch
        val label: ImageView

        init {
            textView = itemView.findViewById(R.id.type) as TextView
            switch_btn = itemView.findViewById(R.id.switch_btn) as Switch
            label = itemView.findViewById(R.id.label_image) as ImageView
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }




}