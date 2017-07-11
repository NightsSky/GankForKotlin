package com.nightssky.gankforkotlin.Model.Entity

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by user on 2017/6/14.
 */

data class historyData(var isError: Boolean,
                   var results: List<String>)

data class DayData(var isError: Boolean,
                   var results: ResultData,
                   var category: List<String>)

data class ResultData(
        @SerializedName("Android")
        var android: List<DetailsData>,
        @SerializedName("iOS")
        var ios: List<DetailsData>,
        @SerializedName("休息视频")
        var video: List<DetailsData>,
        @SerializedName("前端")
        var js: List<DetailsData>,
        @SerializedName("拓展资源")
        var rec: List<DetailsData>,
        @SerializedName("福利")
        var bonus: List<DetailsData>)

data class typeData(var isError: Boolean,
                   var results: List<DetailsData>)
data class DetailsData(
                       var desc: String,
                       var publishedAt: Date,
                       var type: String,
                       var url: String,
                       var who: String,
                       var images: List<String>,
                       var width:Int,
                       var height:Int)

data class MeiziData(
        var url: String,
        var width:Int,
        var height:Int
)

data class SearchData(
        @SerializedName("count")
        var count: Int,
        var error: Boolean,
        var results: List<CategoryData>)

data class CategoryData(var desc: String,
                        var publishedAt: String,
                        var type: String,
                        var url: String,
                        var who: String)



