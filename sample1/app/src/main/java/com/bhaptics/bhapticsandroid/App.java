/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.bhapticsandroid;

import android.app.Activity;
import android.util.Log;

import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;
import com.bhaptics.service.SimpleBhapticsDevice;

import java.util.Collections;
import java.util.List;

public class App {
    public static final String TAG = App.class.getSimpleName();
    private static SdkRequestHandler requestHandler;

    public static String appId = "yIO5j0nrDFUHo7KSmHBK";
    public static String sdkKey = "9RUMghoaM9e0F9RN7oKy";

    public synchronized static void initialize(Activity context) {
        if (requestHandler == null) {
            requestHandler = new SdkRequestHandler(context);
            requestHandler.initialize(appId, sdkKey, "");
        }

        return;
    }

    public static void refreshPairing() {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot refreshPairing");
            return;
        }

        requestHandler.refreshPairing();
    }

    public static int play(String eventName, float intensity, float duration, float angleX, float offsetY) {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot play");
            return -1;
        }

        return requestHandler.play(appId, eventName, intensity, duration, angleX, offsetY);
    }

    public static List<String> getEventList() {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot getEventList");
            return Collections.emptyList();
        }

        return requestHandler.getEventList(appId);
    }
    public static void ping(String address) {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot ping");
            return;
        }

        requestHandler.ping(address);
    }

    public static List<SimpleBhapticsDevice> getDeviceList() {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot getDeviceList");
            return Collections.emptyList();
        }

        return requestHandler.getDeviceList();
    }

    public static void destroy() {
        requestHandler.quit();
        requestHandler = null;
    }

    public static void submitPath(String key, String position, float[] xArr, float[] yArr, int[] intArr, int duration) {
        if (requestHandler == null) {
            Log.e(TAG, "requestHandler: not initialized cannot submitPath");
            return;
        }

        requestHandler.submitPath(key, position, xArr, yArr, intArr, duration);
    }

    public static void turnOff(String key) {
        requestHandler.turnOff(key);

    }
}
