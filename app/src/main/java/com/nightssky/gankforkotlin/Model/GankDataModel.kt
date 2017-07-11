package com.nightssky.gankforkotlin.Model

import android.Manifest
import android.app.Activity
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.nightssky.gankforkotlin.Model.Entity.DayData
import com.nightssky.gankforkotlin.Model.Entity.SearchData
import com.nightssky.gankforkotlin.Model.Entity.typeData
import com.nightssky.gankforkotlin.View.Common.MyApplication
import com.nightssky.gankforkotlin.utils.FileUtils
import com.nightssky.gankforkotlin.utils.toast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*






/**
 * Created by user on 2017/6/14.
 */
class GankDataModel {
    val calendar:Calendar  by lazy {
        Calendar.getInstance()

    }

    fun getHistoryDate(life: LifecycleProvider<FragmentEvent>, listener: OnGetDataListener<Calendar>) {
        var observer = object : Observer<Date> {
            override fun onError(e: Throwable) {
                e.message?.let {
                 }
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {

            }

            override fun onNext(t: Date) {
                t.let {
                    calendar.time = t
                    listener.success(calendar)
                }
                Log.d("msg", t.toString())
            }


        }
        HttpManager.getInstence.getHistoryDate(observer,life)
    }


    fun getDayData(year: Int, month: Int, day: Int, life: LifecycleProvider<FragmentEvent> ,listener: OnGetDataListener<DayData>) {
        val observer = object : Observer<DayData> {
            override fun onNext(t: DayData) {
                t.let {
                    listener.success(t)
                }
                Log.d("msg", t.toString())
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {

            e.message?.let {
                listener.fail(e.message as String)
            }

            }



            override fun onComplete() {
                Log.d("msg", "onComplete")

            }



        }
        HttpManager.getInstence.getDayData(observer,year,month,day,life)
    }
    fun getTypeData(type:String, num:Int, page:Int, life: LifecycleProvider<FragmentEvent>, TypeListener: OnGetDataListener<typeData>){
        var observer = object :Observer<typeData>{
            override fun onError(e: Throwable) {
                e.message?.let {
                    TypeListener.fail(e.message as String)
                }
            }

            override fun onComplete() {
            }

            override fun onNext(t: typeData) {
                t?.let {
                    TypeListener.success(t)
                }
                Log.d("msg", t.toString())
            }

            override fun onSubscribe(d: Disposable) {
            }

        }
        if (type.equals("福利")) {
            HttpManager.getInstence.getTypeData(observer, num, page, life)
        } else {
            HttpManager.getInstence.getTypeData(observer,type,num,page,life)

        }

    }



    fun getSearchData(query:String,category: String,page:Int, life: LifecycleProvider<ActivityEvent>, TypeListener: OnGetDataListener<SearchData>){
        var observer = object :Observer<SearchData>{
            override fun onError(e: Throwable) {
                e.printStackTrace()
                e.message?.let {
                    TypeListener.fail(e.message as String)
                }
            }

            override fun onComplete() {
            }

            override fun onNext(t: SearchData) {
                t?.let {
                    TypeListener.success(t)
                }
                Log.d("msg", t.toString())
            }

            override fun onSubscribe(d: Disposable) {
            }

        }
        HttpManager.getInstence.getSearchData(observer,query,category,page,life)

    }

    /**
     * 保存图片
     *
     */
    fun downImage(rxPermissions:RxPermissions,activity: Activity,url:String) {
        rxPermissions.let {
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    .subscribe { granted ->
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            Observable.just(url)
                                    .subscribeOn(Schedulers.io())
                                    .map {
                                        val bitmap =   Glide.with(MyApplication.context())
                                                .asBitmap()
                                                .load(url)
                                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                .get()
                                        try {

                                            FileUtils.saveBitmap(bitmap,
                                                    Environment
                                                            .getExternalStorageDirectory().absolutePath
                                                            +File.separator+Math.random())

                                            true
                                        } catch(e: Exception) {
                                            e.printStackTrace()
                                            false
                                        }finally {
                                            bitmap.recycle()
                                        }
                                    }
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object :Observer<Boolean>{
                                override fun onComplete() {
                                }
                                override fun onNext(t: Boolean) {
                                    if (t) {
                                        activity.toast("下载成功啦")
                                    } else {
                                        activity.toast("下载失败")
                                    }
                                }
                                override fun onSubscribe(d: Disposable) {
                                }
                                override fun onError(e: Throwable) {
                                }

                            })
                        } else {
                            // Oups permission denied
                            activity.toast("下载失败,请在权限设置中打开所需权限")
                        }
                    }
        }
    }
}