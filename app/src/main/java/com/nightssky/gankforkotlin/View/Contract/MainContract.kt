package com.nightssky.gankforkotlin.View.Contract

import com.nightssky.gankforkotlin.View.Common.BasePresenter
import com.nightssky.gankforkotlin.View.Common.BaseView

/**
 * Created by 追寻何意？又往何处 on 2017/6/13.
 */
interface MainContract {
    interface View : BaseView {
        fun setViewPager()
    }

    interface Presenter: BasePresenter

}
