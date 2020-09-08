package com.pb.taskcapfront.base

import android.app.Application
import android.content.Context

/**
 * Created by balaji on 7/9/20 11:01 PM
 */


object BaseApplication : Application() {

    lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}