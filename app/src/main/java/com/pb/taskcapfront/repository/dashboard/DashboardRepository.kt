package com.pb.taskcapfront.repository.dashboard

import androidx.lifecycle.MutableLiveData
import com.pb.taskcapfront.model.response.dashboard.APIResponse
import com.pb.taskcapfront.restclient.retrofit.services.RetrofitService
import com.pb.taskcapfront.utils.Const
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.await

/**
 * Created by balaji on 7/9/20 10:58 PM
 */


object DashboardRepository {

    private var authApi = RetrofitService.createService(DashboardApi::class.java)

    suspend fun fetchDashboardRepo() = authApi.fetchDashboardApi(Const.DASHBOARD_URL)

}