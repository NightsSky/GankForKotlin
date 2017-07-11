package com.nightssky.gankforkotlin.View.Fragment


import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Activity.ImageActivity
import com.nightssky.gankforkotlin.View.Activity.WebActivity
import com.nightssky.gankforkotlin.View.Adapter.BaseRecycleViewAdapter
import com.nightssky.gankforkotlin.View.Adapter.GankAdapter
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_base.*


/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : RxFragment() {

    lateinit var adapter:GankAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_base, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        //自动刷新
//        PtrFrameLayout.postDelayed(Runnable { PtrFrameLayout.autoRefresh(false) }, 50)
        PtrFrameLayout.autoRefresh(true,500)
    }


    private fun initView() {
        PtrFrameLayout.setPtrHandler(object : PtrHandler {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
               refresh()
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
            }
        })
        val layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
         adapter = GankAdapter(R.layout.item_gank_recycle_view)
        recyclerView.adapter = adapter
        adapter.setItemClickListener(object :BaseRecycleViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int, v: View) {
                val data = adapter.getItem(position)
                if (data?.type.equals("福利")) {
                    startActivity(Intent(activity, ImageActivity::class.java)
                            .putExtra("url",data?.url))
                } else {
                    startActivity(Intent(activity, WebActivity::class.java)
                            .putExtra("URL",data?.url))
                }
            }

        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                       loadMore()
                    }
                }
            }
        })

    }

    abstract fun refresh()

     open fun loadMore(){

     }

}
