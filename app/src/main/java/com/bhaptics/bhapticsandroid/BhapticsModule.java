/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.bhapticsandroid;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.BhapticsManagerImpl;
import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.bhapticsmanger.HapticPlayerImpl;
import com.bhaptics.ble.PairedDeviceManager;
import com.bhaptics.ble.PairedDeviceManagerImpl;

public class BhapticsModule {
    public static final String TAG = BhapticsModule.class.getSimpleName();
    private static BhapticsManager bhapticsManager;
    private static HapticPlayer hapticPlayer;
    private static PairedDeviceManager pairedDeviceManager;

    public static void initialize(Context context) {
        if (hapticPlayer != null) {
            return;
        }
        pairedDeviceManager = new PairedDeviceManagerImpl(context);
        hapticPlayer = new HapticPlayerImpl();
        bhapticsManager = new BhapticsManagerImpl(context, hapticPlayer, pairedDeviceManager);
    }

    public static void destroy() {

        if (bhapticsManager != null) {
            bhapticsManager.dispose();
        }

        hapticPlayer = null;
        bhapticsManager = null;
    }

    public static BhapticsManager getBhapticsManager() {
        if (bhapticsManager == null) {
            Log.e(TAG, "getBhapticsManager is null. call initialize first. ");
        }

        return bhapticsManager;
    }

    public static HapticPlayer getHapticPlayer() {
        return hapticPlayer;
    }


}
