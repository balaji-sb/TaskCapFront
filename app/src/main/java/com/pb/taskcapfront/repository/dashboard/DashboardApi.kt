package com.pb.taskcapfront.repository.dashboard

import com.pb.taskcapfront.model.response.dashboard.APIResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by balaji on 7/9/20 11:10 PM
 */


interface DashboardApi {

    @GET("{dashboardApi}")
    suspend fun fetchDashboardApi(@Path("dashboardApi") dashboardApi: String): APIResponse

}