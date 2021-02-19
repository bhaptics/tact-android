package com.bhaptics.sample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.commons.PermissionUtils;
import com.bhaptics.commons.model.BhapticsDevice;
import com.bhaptics.commons.model.DotPoint;
import com.bhaptics.commons.model.PositionType;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BhapticsModule.initialize(this);

        checkPermissionAndRequestIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BhapticsModule.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (hasPermission()) {
            scanIfNeeded();
        }
    }

    private void checkPermissionAndRequestIfNeeded() {
        if(hasPermission()) {
            scanIfNeeded();
        } else {
            requestPermission();
        }
    }

    private void playHaptic() {
        HapticPlayer player = BhapticsModule.getHapticPlayer();
        player.submitDot("play", PositionType.VestFront, Arrays.asList(new DotPoint(0, 100)), 1000);
    }

    private boolean hasPermission() {
        boolean blePermission = PermissionUtils.hasBluetoothPermission(this);
        boolean filePermission = PermissionUtils.hasFilePermissions(this);
        return blePermission && filePermission;
    }

    private void requestPermission() {
        PermissionUtils.requestBluetoothPermission(this, 100);
        PermissionUtils.requestFilePermission(this, 101);
    }

    private void scanIfNeeded() {
        BhapticsManager manager = BhapticsModule.getBhapticsManager();

        List<BhapticsDevice> deviceList = manager.getDeviceList();
        boolean hasPairedDevice = false;
        for (BhapticsDevice device : deviceList) {
            if (device.isPaired()) {
                hasPairedDevice = true;
                break;
            }
        }

        if (hasPairedDevice) {
            manager.scan();
        }
    }
}
