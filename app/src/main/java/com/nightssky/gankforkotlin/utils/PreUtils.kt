package com.nightssky.gankforkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.nightssky.gankforkotlin.View.Common.MyApplication


/**
 * Created by user on 2017/6/28.
 */
object PreUtils {
    private val SP_NAME = "GANK"
    private  val sp: SharedPreferences by lazy {
         MyApplication.context().getSharedPreferences(SP_NAME, 0)
    }

    fun saveBoolean(key: String, value: Boolean) {

        sp!!.edit().putBoolean(key, value).commit()
    }

    fun saveString( key: String, value: String) {

        sp!!.edit().putString(key, value).commit()

    }

    fun clear(context: Context) {

        sp!!.edit().clear().commit()
    }


    fun saveLong( key: String, value: Long) {

        sp!!.edit().putLong(key, value).commit()
    }


    fun saveInt(key: String, value: Int) {

        sp!!.edit().putInt(key, value).commit()
    }


    fun getString(context: Context, key: String, defValue: String): String {

        return sp!!.getString(key, defValue)
    }


    fun getInt( key: String, defValue: Int): Int {

        return sp!!.getInt(key, defValue)
    }


    fun getBoolean(key: String, defValue: Boolean): Boolean {

        return sp!!.getBoolean(key, defValue)
    }
}