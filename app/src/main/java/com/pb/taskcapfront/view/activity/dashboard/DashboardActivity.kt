package com.pb.taskcapfront.view.activity.dashboard

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pb.taskcapfront.R
import com.pb.taskcapfront.base.BaseActivity
import com.pb.taskcapfront.model.response.dashboard.Contents
import com.pb.taskcapfront.utils.PackageUtils
import com.pb.taskcapfront.view.adapter.DashboardAdapter
import com.pb.taskcapfront.viewmodel.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_no_network.*
import kotlinx.android.synthetic.main.layout_recycler.*

/**
 * Created by balaji on 7/9/20 10:09 PM
 */

class DashboardActivity : BaseActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mDashboardViewModel: DashboardViewModel
    private var mDashboardAdapter: DashboardAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_dashboard
    }

    override fun getScreenName(): String {
        return fetchScreenName(this)
    }

    override fun initValues() {
        mDashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
    }

    override fun setupViews() {
        btnRetry.setOnClickListener(this)
        dashboardSwipeRefresh.setOnRefreshListener(this)
        genericRecycler?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = DashboardAdapter(this.context)
        }
        checkConnectionAndFetch()
    }

    private fun checkConnectionAndFetch() {
        if (isInternetConnected()) {
            noNetworkLayout.visibility = View.GONE
            dashboardRecycler.visibility = View.VISIBLE
            fetchApi()
        } else {
            noNetworkLayout.visibility = View.VISIBLE
            dashboardRecycler.visibility = View.GONE
            dashboardSwipeRefresh.isRefreshing = false
        }
    }

    private fun isInternetConnected(): Boolean {
        return PackageUtils.isInternetAvailable(this)
    }

    private fun fetchApi() {
        if (!dashboardSwipeRefresh.isRefreshing)
            showProgressBar(true)
        mDashboardViewModel.fetchDashboardApi().observe(this, dashboardObserver)
    }

    private val dashboardObserver = Observer<List<Contents>> { contentsList ->
        contentsList?.apply {
            if (this.isNotEmpty()) {
                showProgressBar(false)
                dashboardSwipeRefresh.isRefreshing = false
                mDashboardAdapter?.setContents(contentsList)
            }
        }
    }

    private fun showProgressBar(progressVisibility: Boolean) {
        if (progressVisibility) dashboardProgress.visibility = View.VISIBLE
        else dashboardProgress.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnRetry -> checkConnectionAndFetch()
        }
    }

    override fun onRefresh() {
        checkConnectionAndFetch()
    }

}