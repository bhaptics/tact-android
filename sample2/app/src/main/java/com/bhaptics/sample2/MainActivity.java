package com.bhaptics.sample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.bhaptics.bhapticsmanger.SdkRequestHandler;
import com.bhaptics.commons.PermissionUtils;
import com.bhaptics.service.SimpleBhapticsDevice;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private SdkRequestHandler sdkRequestHandler;

    private android.os.Handler handler = new android.os.Handler();
    private java.lang.Runnable run = new java.lang.Runnable() {

        @Override
        public void run() {
            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        sdkRequestHandler = new SdkRequestHandler(getApplicationContext(), "appName");
        handler.postDelayed(run, 500);

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
        sdkRequestHandler.quit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void playHaptic() {
        Log.i(TAG, "playHaptic: ");
//        if (sdkRequestHandler.isDeviceConnected(BhapticsManager.DeviceType.Head)) {
//            Log.i(TAG, "playHaptic: head connected");
//            player.submitDot("play", PositionType.Head, Arrays.asList(new DotPoint(0, 100)), 1000);
//        } else {
//            Log.i(TAG, "playHaptic: head not connected");
//            player.submitDot("play", PositionType.VestFront, Arrays.asList(new DotPoint(0, 100)), 1000);
//        }
//        player.submitDot("play_ForearmL", PositionType.ForearmL, Arrays.asList(new DotPoint(0, 100)), 1000);
//        player.submitDot("play_ForearmR", PositionType.ForearmR, Arrays.asList(new DotPoint(0, 100)), 1000);

    }
}