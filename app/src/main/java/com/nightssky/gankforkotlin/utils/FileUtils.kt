package com.nightssky.gankforkotlin.utils

import android.graphics.Bitmap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by user on 2017/7/6.
 */
object FileUtils {

    /**
     * 保存Bitmap为图片
     */
    @Throws(Exception::class)
    fun saveBitmap(bitmap: Bitmap, picPath: String) {
        val f = File(picPath + ".jpg")
        if (f.exists()) {
            f.delete()
        }
        println(f.absolutePath)
        try {
            val out = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            println("下载成功")
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            throw FileNotFoundException()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            throw IOException()
        }
    }

}