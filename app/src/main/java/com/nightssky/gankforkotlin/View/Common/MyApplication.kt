package com.nightssky.gankforkotlin.View.Common

import android.app.Application

/**
 * Created by 追寻何意？又往何处 on 2017/6/15.
 */
class MyApplication : Application() {

    companion object {
        private var instance: Application? = null
        fun context() = instance!!
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}
