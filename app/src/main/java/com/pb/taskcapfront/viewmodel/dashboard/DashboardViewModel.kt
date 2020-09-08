package com.pb.taskcapfront.viewmodel.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pb.taskcapfront.model.response.dashboard.Contents
import com.pb.taskcapfront.repository.dashboard.DashboardRepository
import com.pb.taskcapfront.utils.Const
import kotlinx.coroutines.*
import java.io.IOException

/**
 * Created by balaji on 7/9/20 11:16 PM
 */


class DashboardViewModel : ViewModel() {

    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + coroutineJob)

    fun fetchDashboardApi(): LiveData<List<Contents>> {
        val contentsList = MutableLiveData<List<Contents>>()
        coroutineScope.launch {
            val dashboardResponse = DashboardRepository.fetchDashboardRepo()
            val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
            coroutineScope.launch {
                try {
                    if (dashboardResponse.code == Const.SUCCESS) {
                        contentsList.value = dashboardResponse.contents
                    }
                } catch (th: Throwable) {
                    th.printStackTrace()
                    this.cancel()
                }
            }
        }
        return contentsList
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}