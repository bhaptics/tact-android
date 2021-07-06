
/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.sample3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bhaptics.commons.utils.LogUtils;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = LogUtils.makeLogTag(MainActivity.class);

    private DeviceListFragment deviceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        deviceListFragment = new DeviceListFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, deviceListFragment);
        ft.commit();

        App.initialize(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.destroy();
    }
}
