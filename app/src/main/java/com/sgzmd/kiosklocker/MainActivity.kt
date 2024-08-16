package com.sgzmd.kiosklocker

import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.name;

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnKiosk).setOnClickListener {
            val context = applicationContext;
            val kioskAppPackageName = "com.simplemobiletools.dialer";
            val dpm = context.getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val adminComponent = ComponentName(context, KioskDeviceAdminReceiver::class.java)

            Log.d(TAG, "Setting kiosk mode app to $kioskAppPackageName")
            if (!dpm.isDeviceOwnerApp(context.packageName)) {
                Log.w(TAG, "${context.packageName} is not the device owner, cannot set kiosk mode")
            } else {
                dpm.setLockTaskPackages(adminComponent,
                    arrayOf(kioskAppPackageName, adminComponent.packageName))

                val launchIntent = context.packageManager.getLaunchIntentForPackage(kioskAppPackageName)
                val options = ActivityOptions.makeBasic()
                options.setLockTaskEnabled(true)

                if (launchIntent != null) {
                    context.startActivity(launchIntent, options.toBundle())
                    startLockTask();
                }
            }
        }
    }

}