package com.nightssky.gankforkotlin.View.Activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.nightssky.gankforkotlin.Model.Entity.CategoryData
import com.nightssky.gankforkotlin.Presenter.SearchPresenter
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Adapter.BaseRecycleViewAdapter
import com.nightssky.gankforkotlin.View.Adapter.SearchAdapter
import com.nightssky.gankforkotlin.View.Contract.SearchContract
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*



class SearchActivity : RxAppCompatActivity(),SearchContract.View {

   private val presenter = SearchPresenter(this)
    lateinit var adapter:SearchAdapter
    private var pageIndex = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setView()

    }
    override fun setView() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        search_view.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                showProgress()
                if (!TextUtils.isEmpty(search_view.text.toString())) {
                    presenter.getSearchData(search_view.text.toString(),category.text.toString(),1)
                }
            }

        })
        search_recycler.setHasFixedSize(true)
        search_recycler.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
         adapter = SearchAdapter(R.layout.item_gank_recycle_view)
        search_recycler.adapter = adapter
        adapter.setItemClickListener(object : BaseRecycleViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int, v: View) {
                val data = adapter.getItem(position)
                if (data?.type.equals("福利")) {
                    startActivity(Intent(this@SearchActivity, ImageActivity::class.java)
                            .putExtra("url",data?.url))
                } else {
                    startActivity(Intent(this@SearchActivity, WebActivity::class.java)
                            .putExtra("URL",data?.url))
                }


            }

        })
        search_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        if (anim_layout.visibility == View.GONE) {
                            loadMore()
                        }

                    }
                }
            }
        })

    }

    override fun setData(list: List<CategoryData>) {
        adapter.setData(list)
        hideProgress()
    }

    override fun addData(list: List<CategoryData>) {
        adapter.addData(list)
        hideProgress()
    }

    override fun setEmpty() {
        empty_view.visibility = View.VISIBLE
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
    override fun loadMore() {
        showProgress()
        presenter.loadMore(search_view.text.toString(),category.text.toString(),pageIndex++)
    }



     val imm:InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * 判断点击按下是否在EditText上

     * @param v
     * *
     * @param event
     * *
     * @return
     */
    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v!!.windowToken, 0)
                }
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (window.superDispatchTouchEvent(ev)) {
            return true
        }
        return onTouchEvent(ev)
    }
}
