package com.pb.taskcapfront.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pb.taskcapfront.utils.PackageUtils
import kotlin.reflect.KClass

/**
 * Created by balaji on 7/9/20 10:11 PM
 */


abstract class BaseActivity : AppCompatActivity() {

    private var mContext: Context? = null
    var TAG = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
        setContentView(getLayoutResource())
        TAG = getScreenName()
        initValues()
        setupViews()
    }

    /**
     * navigate activity for classes whoever extends Appcompatactivity
     */

    fun navigateActivity(
        activity: KClass<out AppCompatActivity>,
        bundle: Bundle = Bundle(),
        isNewActivity: Boolean = false
    ) {
        try {
            val intent = Intent(mContext, activity.java)
            if (isNewActivity)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtras(bundle)
            mContext?.startActivity(intent)
            PackageUtils.hideKeyboard(activity.objectInstance)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun fetchScreenName(activity: Activity): String {
        return activity.javaClass.simpleName
    }

    abstract fun getLayoutResource(): Int

    abstract fun getScreenName(): String

    abstract fun initValues()

    abstract fun setupViews()


}