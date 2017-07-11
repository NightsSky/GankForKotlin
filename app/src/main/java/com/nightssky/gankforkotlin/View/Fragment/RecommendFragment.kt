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
class RecommendFragment : BaseFragment(),GankDataContract.View {



    private val presenter = GankDataPresenter(this)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()

    }
    override fun setView() {

    }

    override fun setData(list: List<DetailsData>) {
        if (list.size > 0) {
            adapter.setData(list)
            PtrFrameLayout.refreshComplete()

        } else {
            setEmpty()
        }
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
        PtrFrameLayout.refreshComplete()
        empty_view.visibility = View.VISIBLE

    }

    override fun refresh() {
        presenter.getHistoryDate()
    }

    override fun addData(list: List<DetailsData>) {

    }
}
