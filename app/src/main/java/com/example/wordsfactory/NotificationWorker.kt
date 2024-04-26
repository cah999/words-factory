package com.example.wordsfactory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.data.repository.PreferencesRepositoryImpl
import com.example.wordsfactory.domain.usecase.GetLastTimeTrainingUseCase
import com.example.wordsfactory.presentation.MainActivity
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val useCase = GetLastTimeTrainingUseCase(PreferencesRepositoryImpl(context))
        val lastTimeTraining = useCase.execute()
        val lastTrainingDate =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(lastTimeTraining), ZoneId.systemDefault())
        val testPassedToday = lastTrainingDate.toLocalDate().isEqual(LocalDate.now())

        if (!testPassedToday) {
            sendNotification()
        }

        return Result.success()
    }

    private fun sendNotification() {
        val channelId = Constants.DAILY_NOTIFICATION_TOPIC
        val notificationId = 1

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(
                channelId,
                Constants.DAILY_NOTIFICATION_TOPIC,
                NotificationManager.IMPORTANCE_HIGH
            )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.time_to_practice))
            .setContentText(context.getString(R.string.it_seems_you_haven_t_practiced_today_yet_let_s_do_it))
            .setStyle(NotificationCompat.BigTextStyle())
            .setSmallIcon(R.drawable.gym)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}