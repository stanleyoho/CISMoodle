package com.app.cismoodle.ui.activity

import android.support.v7.app.AppCompatActivity

abstract class BaseActicity : AppCompatActivity(){

    abstract fun initView()

    abstract fun initEvent()
}