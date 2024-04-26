package com.example.wordsfactory.presentation.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WidgetDataSyncWorker(
    private val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        AppWidget().updateAll(context)
        return Result.success()
    }
}