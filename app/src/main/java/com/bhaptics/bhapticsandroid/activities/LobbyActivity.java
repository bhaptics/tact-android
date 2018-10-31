package com.bhaptics.bhapticsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.adapters.ListViewAdapter;
import com.bhaptics.tact.ble.DeviceWatcher;
import com.bhaptics.tact.nav.NativeHapticPlayer;

public class LobbyActivity extends Activity implements View.OnClickListener {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private NativeHapticPlayer hapticPlayer;
    private ListViewAdapter adapter;

    private Button scanButton, drawingButton, tactFileButton, tactotExampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        DeviceWatcher.isPairedListExist(this);
        hapticPlayer = NativeHapticPlayer.getInstance(this);

        adapter = new ListViewAdapter(this, hapticPlayer.getDevices());
        hapticPlayer.setChangeDeviceListCallback(adapter);
        ListView listview = (ListView) findViewById(R.id.deviceListView) ;
        listview.setAdapter(adapter) ;

        scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(this);

        drawingButton = findViewById(R.id.drawing_button);
        drawingButton.setOnClickListener(this);

        tactFileButton = findViewById(R.id.tact_file_button);
        tactFileButton.setOnClickListener(this);

        tactotExampleButton = findViewById(R.id.tactot_file_button);
        tactotExampleButton.setOnClickListener(this);

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Permission is not granted
            Log.e(TAG, "onResume: permission ACCESS_FINE_LOCATION"  );
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            hapticPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hapticPlayer.stop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {
            hapticPlayer.startScan();
        } else if (v.getId() == R.id.drawing_button) {
            startActivityForResult(new Intent(this, DrawingActivity.class), 1);
        } else if (v.getId() == R.id.tact_file_button) {
            startActivityForResult(new Intent(this, TactFileActivity.class), 1);
        } else {
            startActivityForResult(new Intent(this, VestDemoActivity.class), 1);
        }
    }
}
