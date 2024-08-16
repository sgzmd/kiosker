package com.sgzmd.kiosklocker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class KioskForegroundService : Service() {

    companion object {
        private const val TAG = "KioskForegroundService"
        private const val CHANNEL_ID = "kiosk_mode_channel"
        private const val CHANNEL_NAME = "Kiosk Mode"
        private const val NOTIFICATION_ID = 1

        fun startService(context: Context) {
            val startIntent = Intent(context, KioskForegroundService::class.java)
            context.startForegroundService(startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, KioskForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Creating foreground service")

        createNotificationChannel()

        Log.d(TAG, "Starting foreground service")

        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Kiosk Mode Active")
            .setContentText("This device is controlled by KioskModeLocker")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Change this to your app's icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Make notification persistent
            .build()
    }
}
