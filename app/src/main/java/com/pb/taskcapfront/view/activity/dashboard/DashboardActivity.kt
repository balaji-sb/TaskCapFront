package com.pb.taskcapfront.view.activity.dashboard

import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pb.taskcapfront.R
import com.pb.taskcapfront.base.BaseActivity
import com.pb.taskcapfront.model.response.dashboard.Contents
import com.pb.taskcapfront.utils.PackageUtils
import com.pb.taskcapfront.view.adapter.DashboardAdapter
import com.pb.taskcapfront.viewmodel.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 * Created by balaji on 7/9/20 10:09 PM
 */

class DashboardActivity : BaseActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mDashboardViewModel: DashboardViewModel
    private var dashboardAdapter: DashboardAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_dashboard
    }

    override fun getScreenName(): String {
        return fetchScreenName(this)
    }

    override fun initValues() {
        mDashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardAdapter = DashboardAdapter(this)
        linearLayoutManager = LinearLayoutManager(this)
    }

    override fun setupViews() {
        val dashboardRecyclerView =
            dashboardRecycler.findViewById<RecyclerView>(R.id.genericRecycler)
        val btnRetry =
            noNetworkLayout.findViewById<Button>(R.id.btnRetry)
        btnRetry.setOnClickListener(this)
        dashboardSwipeRefresh.setOnRefreshListener(this)
        dashboardRecyclerView?.apply {
            layoutManager = linearLayoutManager
            adapter = dashboardAdapter
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
                dashboardAdapter?.setContents(contentsList)
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