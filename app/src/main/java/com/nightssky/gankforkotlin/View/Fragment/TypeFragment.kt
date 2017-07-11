package com.nightssky.gankforkotlin.View.Fragment


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.Presenter.GankDataPresenter
import com.nightssky.gankforkotlin.View.Contract.GankDataContract
import kotlinx.android.synthetic.main.fragment_base.*


/**
 * A simple [Fragment] subclass.
 */
class TypeFragment : BaseFragment(), GankDataContract.View {



    private val presenter = GankDataPresenter(this)
    private var pageIndex = 2
    override fun setView() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress()
    }
    override fun setData(list: List<DetailsData>) {
        adapter.setData(list)
        if (list.size==0) setEmpty()
        PtrFrameLayout.refreshComplete()
        hideProgress()
    }
    override fun addData(list: List<DetailsData>) {
        adapter.addData(list)
        hideProgress()
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

    override fun hideProgress() {
        anim_layout.visibility = View.GONE

    }

    override fun setEmpty() {
        empty_view.visibility = View.VISIBLE
    }
    override fun refresh() {
        pageIndex=2
        presenter.getTypeData(typeStr,10,1)

    }
    var typeStr:String="Android"

    fun setType(type:String):BaseFragment{

        typeStr = type
        return this

    }
    override fun loadMore() {

        presenter.loadMore(typeStr,10,pageIndex++)
        showProgress()
    }
}
