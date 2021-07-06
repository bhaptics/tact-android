/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.sample3;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.commons.PermissionUtils;
import com.bhaptics.commons.model.BhapticsDevice;
import com.bhaptics.commons.model.PositionType;
import com.bhaptics.commons.utils.LogUtils;

import java.util.List;

public class DeviceListFragment extends Fragment {
    public static final String TAG = LogUtils.makeLogTag(DeviceListFragment.class);

    private ListView listViewDevices;
    private BhapticsDeviceArrayAdapter adapter;

    private Button buttonPingAll, buttonScan, buttonStream;
    private Button buttonDotMode, buttonPathMode, buttonSubmit;
    private String key1 = "Submit1";
    private String key2 = "Submit2";
    private String key3 = "Submit3";
    private String key4 = "Submit4";

    private String value1 = "";
    private String value2 = "";
    private String value3 = "";
    private String value4 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        value1 = ConfReader.readTactFile(getContext(), R.raw.coil3);
        value2 = ConfReader.readTactFile(getContext(), R.raw.arm_right);
        value3 = ConfReader.readTactFile(getContext(), R.raw.recoil_right_3);
        value4 = ConfReader.readTactFile(getContext(), R.raw.recoil_left_3);
    }


    private final int interval = 1000; // 1 Second
    private Handler refreshHandler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            if (refreshHandler == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                    refreshHandler.postDelayed(runnable, interval);

                }
            });
        }
    };



    private void runCountdown() {
        int count = 0;
        new CountDownTimer(10000, 500) {
            int index = 1;
            public void onTick(long millisUntilFinished) {
                HapticPlayer player = App.getPlayer(index);
                if (!player.isRegistered(key1)) {
                    player.registerProject(key1, value1);
                }
                if (!player.isRegistered(key2)) {
                    player.registerProject(key2, value2);
                }
                if (!player.isRegistered(key3)) {
                    player.registerProject(key3, value3);
                }
                if (!player.isRegistered(key4)) {
                    player.registerProject(key4, value4);
                }
                long start = System.nanoTime();

                player.submitRegistered(key1,
                        key1, 0.5f, 2f, 359, 0);
                player.submitRegistered(key2,
                        key2, 0.5f, 2f, 359, 0);
                player.submitRegistered(key3,
                        key3, 0.5f, 2f, 359, 0);



                long end = System.nanoTime();
                long microseconds = (end - start) / 1000;
                Log.e(TAG, "onTick: end micro sec " + microseconds );
            }

            public void onFinish() {

            }
        }.start();

        new CountDownTimer(10000, 500) {

            public void onTick(long millisUntilFinished) {
//                HapticPlayer player = App.getPlayer(0);
//                if (!player.isRegistered(key1)) {
//                    player.registerProject(key1, value1);
//                }
//                if (!player.isRegistered(key2)) {
//                    player.registerProject(key2, value2);
//                }
//                if (!player.isRegistered(key3)) {
//                    player.registerProject(key3, value3);
//                }
//                if (!player.isRegistered(key4)) {
//                    player.registerProject(key4, value4);
//                }
//
//                player.getPositionStatus(PositionType.FootL);
//                player.getPositionStatus(PositionType.Vest);
//                player.getPositionStatus(PositionType.VestFront);
//                player.getPositionStatus(PositionType.VestBack);
            }

            public void onFinish() {

            }
        }.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.devices_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonScan = view.findViewById(R.id.button_scan);
        buttonStream = view.findViewById(R.id.button_stream);
        buttonPingAll = view.findViewById(R.id.buttonPingAll);
        buttonDotMode = view.findViewById(R.id.buttonDotMode);
        buttonPathMode = view.findViewById(R.id.buttonPathMode);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonPingAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BhapticsDevice> deviceList = App.getBhapticsManager().getDeviceList();
                for (BhapticsDevice simpleBhapticsDevice : deviceList) {
                    App.getBhapticsManager().ping(simpleBhapticsDevice.getAddress());
                }
            }
        });

        buttonStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionUtils.hasBluetoothPermission(getContext())) {
                    Toast.makeText(getContext(), "no permission", Toast.LENGTH_LONG).show();

                    PermissionUtils.requestBluetoothPermission(getActivity(), 1);

                    return;
                }

                if (App.getBhapticsManager().isScanning()) {
                    App.getBhapticsManager().stopScan();
                } else {
                    App.getBhapticsManager().scan();
                }


                if (App.getBhapticsManager().isScanning()) {
                    buttonScan.setText("Scanning");
                } else {
                    buttonScan.setText("Scan");
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runCountdown();
            }
        });

        buttonDotMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BhapticsService hapticsService = App.bhapticsServiceClient.getHapticsService();
//
//                if (hapticsService == null) {
//                    Log.e(TAG, "register: hapticService null" );
//                    return;
//                }
//
//                byte[] bytes = new byte[20];
//                bytes[0] = 100;
//                bytes[2] = 100;
//
//                App.requestHandler.hapticEventDot(appName, "test1",
//                        PositionType.ForearmL.toString(), bytes, 1000);
//                hapticsService.hapticEventDot(appName, "test2",
//                        PositionType.VestFront.toString(), bytes, 1000);

                adapter.notifyDataSetChanged();
            }
        });

        buttonPathMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                float[] x= new float[] {0.5f, 0.5f};
                float[] y= new float[] {0.f, 0.5f};
                int[] inten = new int[] {100, 100};

                App.getPlayer(0).submitPath("test1",
                        PositionType.ForearmL.toString(), x, y, inten, 1000);
                App.getPlayer(0).submitPath("test2",
                        PositionType.VestFront.toString(), x, y, inten, 1000);
            }
        });

        adapter = new BhapticsDeviceArrayAdapter(getContext(), R.layout.bhaptics_device_item);

        listViewDevices = view.findViewById(R.id.listViewDevices);
        listViewDevices.setAdapter(adapter) ;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        Log.e(TAG, "onAttach: " );
        super.onAttach(context);
        refreshHandler =  new Handler();
        refreshHandler.postDelayed(runnable, interval);
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach: " );
        super.onDetach();
        refreshHandler.removeCallbacks(runnable);
        refreshHandler = null;
    }
}
