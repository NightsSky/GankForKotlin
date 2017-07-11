package com.nightssky.gankforkotlin.Model

/**
 * Created by 追寻何意？又往何处 on 2017/5/29.
 */

interface OnGetDataListener<T> {
    fun success(response: T?)
    fun fail(msg: String)
}
