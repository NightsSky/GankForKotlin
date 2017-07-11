package com.nightssky.gankforkotlin.Presenter

import com.nightssky.gankforkotlin.Model.Entity.SearchData
import com.nightssky.gankforkotlin.Model.GankDataModel
import com.nightssky.gankforkotlin.Model.OnGetDataListener
import com.nightssky.gankforkotlin.View.Contract.SearchContract
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by user on 2017/7/3.
 */
class SearchPresenter (val View: SearchContract.View) :SearchContract.Presenter {

    val model: GankDataModel = GankDataModel()
    override fun getSearchData(query: String, category: String, page: Int) {
        model.getSearchData(query,category,page,View as RxAppCompatActivity,object :OnGetDataListener<SearchData>{
            override fun success(response: SearchData?) {
                response?.let{
                    View.setData(response.results)
                }
            }

            override fun fail(msg: String) {
                    View.hideProgress()
                    View.setEmpty()
            }

        })
    }

    override fun loadMore(query: String, category: String, page: Int) {
        model.getSearchData(query,category,page,View as RxAppCompatActivity,object :OnGetDataListener<SearchData>{
            override fun success(response: SearchData?) {
                response?.let{

                    View.addData(response.results)
                }
            }

            override fun fail(msg: String) {
                View.hideProgress()
            }

        })
    }

    override fun start() {

    }


}