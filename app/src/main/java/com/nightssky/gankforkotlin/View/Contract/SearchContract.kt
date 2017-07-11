package com.nightssky.gankforkotlin.View.Contract

import com.nightssky.gankforkotlin.Model.Entity.CategoryData
import com.nightssky.gankforkotlin.View.Common.BasePresenter
import com.nightssky.gankforkotlin.View.Common.BaseView

/**
 * Created by 追寻何意？又往何处 on 2017/6/13.
 */
interface SearchContract {
    interface View : BaseView {
        fun setData(list:List<CategoryData>)
        fun addData(list:List<CategoryData>)
        fun setEmpty()
        fun showProgress()
        fun hideProgress()
        fun loadMore()
    }

    interface Presenter: BasePresenter {
        fun getSearchData(query:String, category:String , page:Int = 1)
        fun loadMore(query:String, category:String , page:Int = 1)
    }

}
