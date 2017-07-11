package com.nightssky.gankforkotlin.Presenter

import android.util.Log
import com.nightssky.gankforkotlin.Model.Entity.DayData
import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.Model.Entity.typeData
import com.nightssky.gankforkotlin.Model.GankDataModel
import com.nightssky.gankforkotlin.Model.OnGetDataListener
import com.nightssky.gankforkotlin.View.Contract.GankDataContract
import com.trello.rxlifecycle2.components.support.RxFragment
import java.util.*

/**
 * Created by 追寻何意？又往何处 on 2017/6/14.
 */
class GankDataPresenter(val View:GankDataContract.View) :GankDataContract.Presenter{

    val model: GankDataModel = GankDataModel()

    override fun start() {
        getHistoryDate()
        View.showProgress()
    }
    override fun getHistoryDate() {
        model.getHistoryDate(View as RxFragment,object : OnGetDataListener<Calendar>{
            override fun success(response: Calendar?) {
                response.let {
                    getDayData(response!!)
                }
            }
            override fun fail(msg: String) {

            }
        })
    }

    override fun getDayData(calendar: Calendar) {
        model.getDayData(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH)
                ,View as RxFragment,object :OnGetDataListener<DayData>{
            override fun success(response: DayData?) {
                response?.let {
                    var list = mutableListOf<DetailsData>()
                    response.category.forEach {
                        when (it) {
                            "Android" -> list.addAll(response.results.android)
                            "休息视频" -> list.addAll(response.results.video)
                            "福利" -> list.addAll(response.results.bonus)
                            "iOS" -> list.addAll(response.results.ios)
                            "前端" -> list.addAll(response.results.js )
                        }
                    }
                    View.setData(list)
                }
            }

            override fun fail(msg: String) {
                View.setEmpty()
            }

        })
    }
    override fun getTypeData(type: String, num: Int, page: Int) {
        model.getTypeData(type,num,page,View as RxFragment,object :OnGetDataListener<typeData>{
            override fun success(response: typeData?) {

                response?.let{
                    View.setData(response.results)
                }


            }

            override fun fail(msg: String) {
                Log.e("msg", msg)
                View.setEmpty()
            }

        })
    }

    /**
     * 加载更多
     */
    override fun loadMore(type: String, num: Int, page: Int) {
        model.getTypeData(type,num,page,View as RxFragment,object :OnGetDataListener<typeData>{
            override fun success(response: typeData?) {

                response?.let{
                    if (response.results.size > 0) {
                        View.addData(response.results)
                    }
                }

            }

            override fun fail(msg: String) {

            }

        })
    }
}