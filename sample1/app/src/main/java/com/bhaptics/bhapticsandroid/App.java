/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.bhapticsandroid;

import android.content.Context;

import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;

public class App {
    private static SdkRequestHandler requestHandler;

    public synchronized static SdkRequestHandler getHandler(Context context) {
        if (requestHandler == null) {
            requestHandler = new SdkRequestHandler(context, "app");
        }

        return requestHandler;
    }

    public static void destroy() {
        BhapticsModule.destroy();
        requestHandler.quit();
    }
}
