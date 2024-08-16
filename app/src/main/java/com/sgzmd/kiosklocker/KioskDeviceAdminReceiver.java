package com.sgzmd.kiosklocker;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

public class KioskDeviceAdminReceiver extends DeviceAdminReceiver {
    private static final String TAG = KioskDeviceAdminReceiver.class.getName();

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);

        Log.d(TAG, "Device admin enabled");
    }
}
