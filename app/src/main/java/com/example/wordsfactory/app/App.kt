package com.example.wordsfactory.app

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wordsfactory.presentation.notification.NotificationWorker
import com.example.wordsfactory.R
import com.example.wordsfactory.di.appModule
import com.example.wordsfactory.di.dataModule
import com.example.wordsfactory.di.domainModule
import com.example.wordsfactory.presentation.widget.WidgetDataSyncWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val workManager = WorkManager.getInstance(this)

        val dataSyncWorkName = getString(R.string.datasyncwork)
        val dataSyncWorkRequest = PeriodicWorkRequestBuilder<WidgetDataSyncWorker>(15, TimeUnit.MINUTES)
            .addTag(dataSyncWorkName)
            .build()

        val dataSyncWorkInfo = workManager.getWorkInfosByTag(dataSyncWorkName).get()
        if (dataSyncWorkInfo.isEmpty() || dataSyncWorkInfo.all { it.state.isFinished }) {
            workManager.enqueue(dataSyncWorkRequest)
        }

        val now = LocalDateTime.now()
        val nextRun = now.with(LocalTime.of(20, 0))
        val delay = Duration.between(now, nextRun)

        val notificationWorkName = getString(R.string.notificationwork)
        val notifyWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(delay)
            .addTag(notificationWorkName)
            .build()

        val notificationWorkInfo = workManager.getWorkInfosByTag(notificationWorkName).get()
        if (notificationWorkInfo.isEmpty() || notificationWorkInfo.all { it.state.isFinished }) {
            workManager.enqueue(notifyWorkRequest)
        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}