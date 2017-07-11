package com.nightssky.gankforkotlin.View.Contract

import com.nightssky.gankforkotlin.Model.Entity.DetailsData
import com.nightssky.gankforkotlin.View.Common.BasePresenter
import com.nightssky.gankforkotlin.View.Common.BaseView
import java.util.*

/**
 * Created by 追寻何意？又往何处 on 2017/6/13.
 */
interface GankDataContract {
    interface View : BaseView {
        fun setData(list:List<DetailsData>)
        fun addData(list:List<DetailsData>)
        fun setEmpty()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter: BasePresenter {
        fun getHistoryDate()
        fun getDayData(calendar: Calendar)
        fun getTypeData(type:String, num:Int = 10, page:Int = 1)
        fun loadMore(type:String, num:Int = 10, page:Int = 2)
    }

}
