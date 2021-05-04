package com.bhaptics.sample2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;


/////////////////
import com.bhaptics.bhapticsmanger.SdkRequestHandler;
import com.bhaptics.service.SimpleBhapticsDevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
///////////////////////////////


public class Ue4GameActivity extends Activity {

    private SdkRequestHandler sdkRequestHandler;
    private String LatestDeviceStatus = "";
    private static final String TAG = "HAPTIC_WRAPPER";

    public native void nativeOnDeviceFound(String deviceListString);
    public native void nativeOnChangeScanState();
    public native void nativeOnChangeResponse();

    //Helper Functions
    private static String DeviceToJsonString(List<SimpleBhapticsDevice> devices) {
        JSONArray jsonArray = new JSONArray();

        for (SimpleBhapticsDevice device : devices) {
            JSONObject obj = DeviceToJsonObject(device);

            if (obj == null) {
                android.util.Log.i(TAG, "toJsonString: failed to parse. " + device);
                continue;
            }
            jsonArray.put(obj);
        }

        return jsonArray.toString();
    }

    private static JSONObject DeviceToJsonObject(SimpleBhapticsDevice device) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("DeviceName", device.getDeviceName());
            obj.put("Address", device.getAddress());
            obj.put("Position", device.getPosition());
            obj.put("ConnectionStatus", device.isConnected() ? "Connected" : "Disconnected");
            obj.put("IsPaired", device.isPaired());
            return obj;
        } catch (JSONException e) {
            android.util.Log.d(TAG, "toJsonObject: " + e.getMessage());
        }
        return null;
    }

    //Bluetooth Functionality
    public void AndroidThunkJava_Scan() {
        if (!com.bhaptics.commons.PermissionUtils.hasBluetoothPermission(this)) {
            return;
        }

        if (!sdkRequestHandler.isScanning()) {
            sdkRequestHandler.toggleScan();
        }
    }

    public void AndroidThunkJava_StopScan() {
        if (sdkRequestHandler.isScanning()) {
            sdkRequestHandler.toggleScan();
        }
    }

    public boolean AndroidThunkJava_IsScanning() {
        return sdkRequestHandler.isScanning();
    }

    // Device Settings
    public void AndroidThunkJava_Pair(String address) {
        sdkRequestHandler.pair(address);
    }

    // Pair Device with positoin
    public void AndroidThunkJava_PairFromPosition(String address, String position) {
        sdkRequestHandler.pair(address);
        AndroidThunkJava_ChangePosition(address, position);
    }

    public void AndroidThunkJava_Unpair(String address) {
        sdkRequestHandler.unpair(address);
    }

    public void AndroidThunkJava_UnpairAll() {
        List<SimpleBhapticsDevice> deviceList = sdkRequestHandler.getDeviceList();

        for (SimpleBhapticsDevice device : deviceList) {
            if (device.isPaired()) {
                sdkRequestHandler.unpair(device.getAddress());
            }
        }
    }

    public void AndroidThunkJava_ChangePosition(String address, String position){
        int i = SimpleBhapticsDevice.stringToPosition(position);

        for (SimpleBhapticsDevice device : sdkRequestHandler.getDeviceList()) {
            if (device.getAddress().equals(address)) {
                if (device.getPosition() != i) {
                    sdkRequestHandler.togglePosition(address);
                }
                break;
            }
        }
    }

    public void AndroidThunkJava_TogglePosition(String address) {
        sdkRequestHandler.togglePosition(address);
    }

    public void AndroidThunkJava_Ping(String address) {
        sdkRequestHandler.ping(address);
    }
    public void AndroidThunkJava_PingAll() {
        List<SimpleBhapticsDevice> deviceList = sdkRequestHandler.getDeviceList();

        for (SimpleBhapticsDevice device : deviceList) {
            if (device.isConnected()) {
                sdkRequestHandler.ping(device.getAddress());
            }
        }
    }

    public String AndroidThunkJava_GetDeviceList() {
        List<SimpleBhapticsDevice> deviceList = sdkRequestHandler.getDeviceList();
        return DeviceToJsonString(deviceList);
    }

    public boolean AndroidThunkJava_GetLatestScanStatus() {
        return sdkRequestHandler.isScanning();
    }

    public void AndroidThunkJava_SubmitRegistered(String key, String altKey,
                                                  float intensity, float duration,
                                                  float offsetAngleX, float offsetY) {
        sdkRequestHandler.submitRegistered(key, altKey, intensity, duration, offsetAngleX, offsetY);
    }

    void  AndroidThunkJava_SubmitDot(String key, String position, int[] indexes, int[] intensities, int duration) {
        sdkRequestHandler.submitDot(key, position, indexes, intensities, duration);
    }
    void  AndroidThunkJava_SubmitPath(String key, String position, float[] x, float[] y, int[] intensities, int duration) {
        sdkRequestHandler.submitPath(key, position, x, y, intensities, duration);
    }

    public void AndroidThunkJava_Register(String key, String fileStr) {
        sdkRequestHandler.register(key, fileStr);
    }
    public void AndroidThunkJava_RegisterReflected(String key, String fileStr) {
        sdkRequestHandler.registerReflected(key, fileStr);
    }
    public void AndroidThunkJava_TurnOff(String key) {
        sdkRequestHandler.turnOff(key);
    }

    public void AndroidThunkJava_TurnOffAll() {
        sdkRequestHandler.turnOffAll();
    }

    public byte[] AndroidThunkJava_GetPositionStatus(String position) {
        return  sdkRequestHandler.getPositionStatus(position);
    }

    public boolean AndroidThunkJava_IsRegistered(String key) {
        return sdkRequestHandler.isRegistered(key);
    }
    public boolean AndroidThunkJava_IsPlaying(String key) {
        return sdkRequestHandler.isPlaying(key);
    }
    public boolean AndroidThunkJava_IsAnythingPlaying() {
        return sdkRequestHandler.isAnythingPlaying();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sdkRequestHandler = new SdkRequestHandler(this, "appName");
//        player = new com.bhaptics.bhapticsmanger.HapticPlayerImpl();
//        bhapticsManager = new com.bhaptics.bhapticsmanger.BhapticsManagerImpl(this, player, new com.bhaptics.ble.PairedDeviceManagerImpl(this));
//        bhapticsManager.addBhapticsManageCallback(new com.bhaptics.bhapticsmanger.BhapticsManagerCallback() {
//            @Override
//            public void onDeviceUpdate(List<com.bhaptics.commons.model.BhapticsDevice> deviceList) {
//                LatestDeviceStatus = DeviceToJsonString(deviceList);
//                nativeOnDeviceFound(LatestDeviceStatus);
//            }
//
//            @Override
//            public void onScanStatusChange(boolean scanning) {
//                nativeOnChangeScanState();
//            }
//
//            @Override
//            public void onChangeResponse() {
//                nativeOnChangeResponse();
//            }
//
//            @Override
//            public void onConnect(String address) {
//                android.util.Log.i("MAIN_ACTIVITY", "onConnect: " + address );
//            }
//            @Override
//            public void onDisconnect(String address) {
//                android.util.Log.i("MAIN ACTIVITY", "onDisconnect: " + address );
//            }
//        });
    }
}
