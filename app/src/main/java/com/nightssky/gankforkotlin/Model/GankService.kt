package com.nightssky.gankforkotlin.Model

import com.nightssky.gankforkotlin.Model.Entity.DayData
import com.nightssky.gankforkotlin.Model.Entity.SearchData
import com.nightssky.gankforkotlin.Model.Entity.historyData
import com.nightssky.gankforkotlin.Model.Entity.typeData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by 追寻何意？又往何处 on 2017/6/13.
 */
interface GankService {

    /**
     * 获取发过干货日期接口:
     * http://gank.io/api/day/history
     */
    @GET("day/history")
    fun getHistoryDate():Observable<historyData>



    /**
     * 每日推荐
     */
    @GET("day/{year}/{month}/{day}")
    fun getDayGanHuo(@Path("year") year: Int, @Path("month") month: Int
                     , @Path("day") day: Int): Observable<DayData>

    /**
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("data/{type}/{num}/{page}")
    fun getTypeData(@Path("type") type: String,@Path("num") num:Int
    ,@Path("page") page:Int): Observable<typeData>

    /**
     * 搜索 API
     *   http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     *   注：
     *   category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     *   count 最大 50
     */
    @GET("search/query/{query}/category/{category}/count/10/page/{page}")
    fun getSearchData(@Path("query") query: String,@Path("category") category:String
                      ,@Path("page") page:Int): Observable<SearchData>


}