package com.nightssky.gankforkotlin.Model

import android.os.Environment


/**
 * Created by user on 2017/6/15.
 */
object Constants {
   var TYPELIST = mutableListOf<String>("Android_T","iOS_T","休息视频_T","拓展资源_T","前端_T","福利_T")

   //记录是否改变
   var TABISCHANGED = false

    var dir = Environment.getExternalStorageDirectory().absolutePath + "/Gank/"

}

//扩展
fun MutableList<String>.getAllList():MutableList<String> {
    var list:MutableList<String> = mutableListOf()
    this.forEach {
        list.add( it.substring(0,it.length-2))
    }
    return list
}

/**
 * 只获取显示的类型
 */
fun MutableList<String>.getList():MutableList<String> {
    var list:MutableList<String> = mutableListOf()
    this.forEach {
        if (it[it.length-1].toString().equals("T")){
            list.add( it.substring(0,it.length-2))
        }
    }
    return list
}

/**
 * 设置是否显示
 */
fun MutableList<String>.setShow(position:Int,isChecked:Boolean) {
    var type = this[position]
    type = "${type.substring(0,type.length-1)}${if (isChecked)"T" else "F"}"
    this[position] = type
}
fun MutableList<String>.getShow(position:Int):Boolean {
    var type = this[position]
    //不得不说这提示功能就是TMD强大
    //TMD巨坑
    return type[type.length-1].toString().equals("T")
}
//重新排序
fun <T> MutableList<T>.swap(fromPosition: Int, toPosition: Int) {
    val tmp = this[fromPosition]
    this[fromPosition] = this[toPosition]
    this[toPosition] = tmp
}