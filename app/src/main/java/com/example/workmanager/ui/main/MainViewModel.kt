package com.example.workmanager.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.workers.MyPeriodicWorker
import com.example.workmanager.workers.MySingleWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private var _s_uuid: MutableLiveData<String> = MutableLiveData()
    val s_uuid: LiveData<String>
        get() = _s_uuid

    fun start_single_task() {
        val mySingleWorkRequest1 = OneTimeWorkRequestBuilder<MySingleWorker>().build()
        WorkManager.getInstance(Application()).enqueue(mySingleWorkRequest1)
    }

    fun start_periodic_task() {
        val myPeriodicWorkRequest =
            PeriodicWorkRequestBuilder<MyPeriodicWorker>(15, TimeUnit.MINUTES)
                .addTag("MyPeriodicWorker")
                .build()
        _s_uuid.value = myPeriodicWorkRequest.id.toString()
        WorkManager.getInstance(Application()).enqueue(myPeriodicWorkRequest)
    }

    fun cancel_periodic_task() {
        WorkManager.getInstance(Application()).cancelAllWorkByTag("MyPeriodicWorker")
    }

    fun get_status_periodic_task(uuid:String) {
        if(uuid == "noValue"){
            Log.i("alain", "No periodic task launched")
        }else{
            val workInfoState =
                WorkManager.getInstance(Application()).getWorkInfoById(UUID.fromString(uuid)).get().state
            Log.i("alain", "State: " + workInfoState + " uuid= "+ uuid)
        }
    }

    fun start_single_task_with_constraint() {
        val myTaskConstaints = Constraints.Builder().setRequiresCharging(true).build()
        val mySingleWorkRequest2 = OneTimeWorkRequestBuilder<MySingleWorker>()
            .setConstraints(myTaskConstaints)
            .build()
        WorkManager.getInstance(Application()).enqueue(mySingleWorkRequest2)
    }
}