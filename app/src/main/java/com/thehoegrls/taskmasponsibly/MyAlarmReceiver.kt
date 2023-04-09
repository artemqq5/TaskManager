package com.thehoegrls.taskmasponsibly

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class MyAlarmReceiver : BroadcastReceiver() {
    private val channelId = "app.notification.channel"
    private val description = "notification_manager"


    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val text = intent?.extras?.getString("textData") ?: "none"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentText(text)

            notificationManager.notify(Random.nextInt(0, 100000), builder.build())
        } else {
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLights(Color.GREEN, 1000, 1000)
                .setVibrate(longArrayOf(0, 100, 200, 300))

            notificationManager.notify(Random.nextInt(0, 100000), builder.build())

        }


    }
}