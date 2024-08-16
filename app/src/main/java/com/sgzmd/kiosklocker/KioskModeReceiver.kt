package com.sgzmd.kiosklocker

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

class KioskModeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "KioskModeReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponent = ComponentName(context, KioskDeviceAdminReceiver::class.java)

        Log.d(TAG, "onReceive called with action ${intent.action}")

        when (intent.action) {
            "com.sgzmd.kiosklocker.ACTION_SET_KIOSK_PACKAGE" -> {
                val packageName = intent.getStringExtra("package_name")

                Log.d(TAG, "Setting kiosk mode app to $packageName")
                if (!dpm.isDeviceOwnerApp(context.packageName)) {
                    Log.w(TAG, "${context.packageName} is not the device owner, cannot set kiosk mode")
                } else {
                    if (packageName != null) {
                        // Start the foreground service
                        KioskForegroundService.startService(context)

                        dpm.setLockTaskPackages(adminComponent, arrayOf(packageName))
                        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
                        launchIntent?.let {
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(it)
                            (context as Activity).startLockTask()
                        }
                    }
                }
            }

            "com.sgzmd.kiosklocker.ACTION_EXIT_KIOSK_MODE" -> {
                if (dpm.isDeviceOwnerApp(context.packageName)) {
                    (context as Activity).stopLockTask()
                    dpm.clearPackagePersistentPreferredActivities(adminComponent, context.packageName)
                    // Stop the foreground service
                    KioskForegroundService.stopService(context)
                }
            }
        }
    }
}
