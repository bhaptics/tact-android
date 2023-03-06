/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.bhapticsandroid;

import android.app.Activity;
import android.content.Context;

import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;

public class App {
    private static SdkRequestHandler requestHandler;

    public synchronized static SdkRequestHandler getHandler(Activity context) {
        if (requestHandler == null) {
            requestHandler = new SdkRequestHandler(context);
        }

        return requestHandler;
    }

    public static void destroy() {
        BhapticsModule.destroy();
        requestHandler.quit();
    }
}
