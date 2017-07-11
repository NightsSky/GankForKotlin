package com.nightssky.gankforkotlin.Model

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.nightssky.gankforkotlin.Model.Entity.DayData
import com.nightssky.gankforkotlin.Model.Entity.SearchData
import com.nightssky.gankforkotlin.Model.Entity.typeData
import com.nightssky.gankforkotlin.View.Common.MyApplication
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by 追寻何意？又往何处 on 2017/6/13.
 */
class HttpManager
private constructor() {

    private val retrofit: Retrofit
    private val contentService: GankService
    private val mOkHttpClient: OkHttpClient

    init {
        //手动创建一个OkHttpClient并设置超时时间和调试日志

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        mOkHttpClient = httpClientBuilder.build()



        retrofit = Retrofit.Builder().client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(GankUrl)
                .build()

        contentService = retrofit.create(GankService::class.java)
    }

    /**
     * 获取发布过干货的日期
     */
    fun getHistoryDate(subscriber: Observer<Date>, provider: LifecycleProvider<FragmentEvent>) {
        contentService.getHistoryDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(provider)
                .map {
                    val list = it.results
                    val str = list[0]
                    val format1 = SimpleDateFormat("yyyy-MM-dd")
                    val date = format1.parse(str)
                    date
                }
                .subscribe(subscriber)

    }

    /**
     * 每日数据
     */
    fun getDayData(subscriber: Observer<DayData>, year: Int, month: Int, day: Int,provider:LifecycleProvider<FragmentEvent>) {
        contentService.getDayGanHuo(year, month, day)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(provider)
                .subscribe(subscriber)

    }

    /**
     * 分类数据
     */
    fun getTypeData(subscriber: Observer<typeData>,type:String,num:Int,page:Int,provider:LifecycleProvider<FragmentEvent>){
        contentService.getTypeData(type,num,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(provider)
                .subscribe(subscriber)

    }

    /**
     * 福利
     */
    fun getTypeData(subscriber: Observer<typeData>,num:Int,page:Int,provider:LifecycleProvider<FragmentEvent>){
        contentService.getTypeData("福利",num,page)
                .subscribeOn(Schedulers.io())

                .bindToLifecycle(provider)
                .map { typeData ->
                     typeData.results.forEach {
                         val bitmap = Glide.with(MyApplication.context())
                                 .asBitmap()
                                 .load(it.url)
                                 .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                 .get()
                         it.width = bitmap.width
                         it.height = bitmap.height
                         bitmap.recycle()
                     }
                    typeData
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)

    }
    /**
     * 搜索数据
     */
    fun getSearchData(subscriber: Observer<SearchData>,query:String,category: String,page:Int,provider:LifecycleProvider<ActivityEvent>) {
        contentService.getSearchData(query,category,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(provider)

                .subscribe(subscriber)
    }



    //在访问HttpMethods时创建单例
    private object SingletonHolder {
        val INSTANCE = HttpManager()
    }
    companion object {
        private val DEFAULT_TIMEOUT = 5
        val GankUrl = "http://gank.io/api/"
        val getInstence: HttpManager
            get() = SingletonHolder.INSTANCE

    }
}

