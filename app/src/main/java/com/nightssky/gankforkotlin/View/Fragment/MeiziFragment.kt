package com.nightssky.gankforkotlin.View.Fragment


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.Presenter.GankDataPresenter
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Activity.ImageActivity
import com.nightssky.gankforkotlin.View.Adapter.BaseRecycleViewAdapter
import com.nightssky.gankforkotlin.View.Adapter.MeiziAdapter
import com.nightssky.gankforkotlin.View.Contract.GankDataContract
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_blank.*




/**
 * A simple [Fragment] subclass.
 */
class MeiziFragment : RxFragment(),GankDataContract.View {


    lateinit var adapter :MeiziAdapter
     lateinit var  presenter:GankDataPresenter
        var pageIndex = 2

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView()
        showProgress()

    }


    override fun setView() {

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        adapter = MeiziAdapter(R.layout.item_girls_recycle)
        meizi_recycle.layoutManager = layoutManager
        meizi_recycle.adapter = adapter
        meizi_recycle.setHasFixedSize(true)
        meizi_recycle.setPadding(0, 0, 0, 0);
        meizi_recycle.itemAnimator = DefaultItemAnimator()

        presenter = GankDataPresenter(this)
        presenter.getTypeData("福利",10,1)
        meizi_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                layoutManager.invalidateSpanAssignments();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        if (anim_layout.visibility == View.GONE) {
                            loadMore()
                        }
                    }
                }
            }
        })

        adapter.setItemClickListener(object :BaseRecycleViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int, v: View) {
//                startActivity(Intent(activity,ImageActivity::class.java)
//                        .putExtra("url",adapter.getItem(position)?.url))
                startActivity(Intent(activity,ImageActivity::class.java)
                        .putExtra("url",adapter.getItem(position)?.url),
                        ActivityOptions.makeSceneTransitionAnimation(activity,v.findViewById(R.id.img_girls) as ImageView,"image").toBundle())
            }

        })
    }

    override fun setData(list: List<DetailsData>) {
        adapter.setData(list)
        hideProgress()
    }

    override fun addData(list: List<DetailsData>) {
        adapter.addData(list)
        hideProgress()
    }
    override fun hideProgress() {
        anim_layout.visibility = View.GONE
    }

    override fun showProgress() {
        anim_layout.visibility = View.VISIBLE
        val animatorx = ObjectAnimator.ofFloat(anim_layout,"scaleX",0f,1f)
        val animatory = ObjectAnimator.ofFloat(anim_layout,"scaleY",0f,1f)
        val animSet = AnimatorSet()
        animSet.duration = 1000
        animSet.play(animatorx).with(animatory)
        animSet.start()
    }
    override fun setEmpty() {
        empty_view.visibility = View.VISIBLE
    }



    fun loadMore() {
        presenter.loadMore("福利",10,pageIndex++)
        showProgress()
    }
}