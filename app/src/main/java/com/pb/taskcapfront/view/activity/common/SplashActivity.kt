package com.pb.taskcapfront.view.activity.common

import android.os.Handler
import android.os.Looper
import com.pb.taskcapfront.R
import com.pb.taskcapfront.base.BaseActivity
import com.pb.taskcapfront.utils.Const
import com.pb.taskcapfront.view.activity.dashboard.DashboardActivity

/**
 * Created by balaji on 8/9/20 6:57 PM
 */


class SplashActivity : BaseActivity() {

    private val handler by lazy { Handler(Looper.myLooper()!!) }

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun getScreenName(): String {
        return fetchScreenName(this)
    }

    override fun initValues() {
        // do nothing
    }

    override fun setupViews() {
        handler.postDelayed(runnable, Const.SPLASH_INTERVAL)
    }

    private val runnable = Runnable {
        navigateActivity(DashboardActivity::class, isNewActivity = true)
    }

    override fun onPause() {
        super.onPause()
        handler.apply {
            removeCallbacks(runnable)
        }
    }

}