package com.example.workmanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MySingleWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        makeStatusNotification(1,"Start Single Worker", applicationContext)
        MySingleTask()
        makeStatusNotification(2, "End Single Worker", applicationContext)
        return Result.success()
    }
    private fun MySingleTask() {
        Thread.sleep(5000)
    }
}