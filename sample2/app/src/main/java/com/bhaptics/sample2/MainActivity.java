package com.bhaptics.sample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

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
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BhapticsModule.initialize(this);

        checkPermissionAndRequestIfNeeded();


        new CountDownTimer(300000, 2000) {

            public void onTick(long millisUntilFinished) {
                playHaptic();
            }

            public void onFinish() {
            }
        }.start();
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
            BhapticsModule.getBhapticsManager().refreshPairingInfo();
            scanIfNeeded();
        }
    }

    private void checkPermissionAndRequestIfNeeded() {
        
        if(hasPermission()) {
            Log.i(TAG, "checkPermissionAndRequestIfNeeded: scanIfNeeded() ");
            scanIfNeeded();
        } else {
            Log.i(TAG, "checkPermissionAndRequestIfNeeded: requestPermission()");
            requestPermission();
        }
    }

    private void playHaptic() {
        Log.i(TAG, "playHaptic: ");
        HapticPlayer player = BhapticsModule.getHapticPlayer();

        if (BhapticsModule.getBhapticsManager().isDeviceConnected(BhapticsManager.DeviceType.Head)) {
            Log.i(TAG, "playHaptic: head connected");
            player.submitDot("play", PositionType.Head, Arrays.asList(new DotPoint(0, 100)), 1000);
        } else {
            Log.i(TAG, "playHaptic: head not connected");
            player.submitDot("play", PositionType.VestFront, Arrays.asList(new DotPoint(0, 100)), 1000);
        }
        player.submitDot("play_ForearmL", PositionType.ForearmL, Arrays.asList(new DotPoint(0, 100)), 1000);
        player.submitDot("play_ForearmR", PositionType.ForearmR, Arrays.asList(new DotPoint(0, 100)), 1000);

    }

    private boolean hasPermission() {
        boolean blePermission = PermissionUtils.hasBluetoothPermission(this);
        boolean filePermission = PermissionUtils.hasFilePermissions(this);
        return blePermission && filePermission;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                1);
    }

    private void scanIfNeeded() {
        Log.i(TAG, "scanIfNeeded: ");
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
            Log.i(TAG, "scan: ");
            manager.scan();
        }
    }
}
