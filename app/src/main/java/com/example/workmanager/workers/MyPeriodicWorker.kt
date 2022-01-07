package com.example.workmanager.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyPeriodicWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): ListenableWorker.Result {
        makeStatusNotification(3, "Start Periodic Worker", applicationContext)
        MySingleTask()
        makeStatusNotification(4, "End Periodic Worker", applicationContext)
        return ListenableWorker.Result.success()
    }
    private fun MySingleTask() {
        Thread.sleep(5000)
    }
}