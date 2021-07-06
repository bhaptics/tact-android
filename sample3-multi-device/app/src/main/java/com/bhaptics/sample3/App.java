/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.sample3;

import android.app.Activity;

import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.HapticPlayer;

public class App {
    public static void initialize(Activity context) {
        BhapticsModule.initialize(context);
    }

    public static void destroy() {
        BhapticsModule.destroy();
    }

    public static BhapticsManager getBhapticsManager() {
        return BhapticsModule.getBhapticsManager();
    }
    public static HapticPlayer getPlayer(int index) {
        return BhapticsModule.getHapticPlayer(index);
    }
}
