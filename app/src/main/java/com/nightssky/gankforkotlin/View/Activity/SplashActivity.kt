package com.nightssky.gankforkotlin.View.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import com.nightssky.gankforkotlin.R
import kotlinx.android.synthetic.main.activity_splash.*




class SplashActivity : AppCompatActivity() {
    private val SPLASH_LENGTH: Long = 2000
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_splash)
        startAnime()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_LENGTH)
    }

    fun startAnime() {

        val animation = AlphaAnimation(0.5f, 1.0f)
        animation.duration = 1500
        root_view.startAnimation(animation)
    }
}
