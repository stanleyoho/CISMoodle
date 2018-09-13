package com.app.cismoodle.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app.cismoodle.R

class SplashActivity : BaseActicity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvent()

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        },1000)
    }

    override fun initView() {
        setContentView(R.layout.activity_splash)
    }

    override fun initEvent() {
    }
}
