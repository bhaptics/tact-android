package com.bhaptics.bhapticsandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.bhaptics.bhapticsandroid.App;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.adapters.ListViewAdapter;
import com.bhaptics.service.SimpleBhapticsDevice;

public class LobbyActivity extends Activity implements View.OnClickListener {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private ListViewAdapter adapter;

    private Button drawingButton, tactotExampleButton, pingallButton;

    private android.os.Handler handler = new android.os.Handler();
    private java.lang.Runnable run = new java.lang.Runnable() {

        @Override
        public void run() {
            handler.postDelayed(this, 500);

            if (adapter != null) {
                adapter.onChangeListUpdate(App.getDeviceList());
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        handler.postDelayed(run, 100);


        App.initialize(this);

        adapter = new ListViewAdapter(this, App.getDeviceList());
        ListView listview = findViewById(R.id.deviceListView) ;
        listview.setAdapter(adapter) ;

        drawingButton = findViewById(R.id.drawing_button);
        drawingButton.setOnClickListener(this);

        tactotExampleButton = findViewById(R.id.tactot_file_button);
        tactotExampleButton.setOnClickListener(this);

        pingallButton = findViewById(R.id.ping_button);
        pingallButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        App.refreshPairing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.destroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ping_button) {
            for (SimpleBhapticsDevice simpleBhapticsDevice : App.getDeviceList()) {
                if (simpleBhapticsDevice.isConnected()) {
                    App.ping(simpleBhapticsDevice.getAddress());
                }
            }
        } else if (v.getId() == R.id.drawing_button) {
            startActivityForResult(new Intent(this, DrawingActivity.class), 1);
        } else {
            startActivityForResult(new Intent(this, VestDemoActivity.class), 1);
        }
    }
}
