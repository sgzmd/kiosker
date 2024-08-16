package com.sgzmd.kiosklocker

import android.app.admin.PolicyUpdateReceiver
import android.app.admin.PolicyUpdateResult
import android.app.admin.TargetUser
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class DevicePolicyResultReceiver : PolicyUpdateReceiver() {
    val TAG = DevicePolicyResultReceiver::class.java.name;

    override fun onPolicySetResult(
        context: Context,
        policyIdentifier: String,
        additionalPolicyParams: Bundle,
        targetUser: TargetUser,
        policyUpdateResult: PolicyUpdateResult
    ) {
        super.onPolicySetResult(
            context,
            policyIdentifier,
            additionalPolicyParams,
            targetUser,
            policyUpdateResult
        )

        Log.d(TAG, "onPolicySetResult called with policyIdentifier $policyIdentifier and result ${policyUpdateResult.resultCode}")
    }

    override fun onPolicyChanged(
        context: Context,
        policyIdentifier: String,
        additionalPolicyParams: Bundle,
        targetUser: TargetUser,
        policyUpdateResult: PolicyUpdateResult
    ) {
        super.onPolicyChanged(
            context,
            policyIdentifier,
            additionalPolicyParams,
            targetUser,
            policyUpdateResult
        )

        Log.d(TAG, "onPolicyChanged called with policyIdentifier $policyIdentifier and result ${policyUpdateResult.resultCode}")
    }
}