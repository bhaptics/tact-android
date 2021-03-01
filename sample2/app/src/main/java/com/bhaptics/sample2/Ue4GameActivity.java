package com.bhaptics.sample2;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;


/////////////////
import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.commons.model.BhapticsDevice;
import com.bhaptics.commons.model.PositionType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
///////////////////////////////


public class Ue4GameActivity extends Activity {

    private BhapticsManager bhapticsManager;
    private HapticPlayer player;
    private String LatestDeviceStatus = "";
    private static final String TAG = "HAPTIC_WRAPPER";

    public native void nativeOnDeviceFound(String deviceListString);
    public native void nativeOnChangeScanState();
    public native void nativeOnChangeResponse();

    //Helper Functions
    private static String DeviceToJsonString(List<BhapticsDevice> devices) {
        JSONArray jsonArray = new JSONArray();

        for (BhapticsDevice device : devices) {
            JSONObject obj = DeviceToJsonObject(device);

            if (obj == null) {
                android.util.Log.i(TAG, "toJsonString: failed to parse. " + device);
                continue;
            }
            jsonArray.put(obj);
        }

        return jsonArray.toString();
    }

    private static JSONObject DeviceToJsonObject(BhapticsDevice device) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("DeviceName", device.getDeviceName());
            obj.put("Address", device.getAddress());
            obj.put("Battery", device.getBattery());
            obj.put("Position", device.getPosition());
            obj.put("ConnectionStatus", device.getConnectionStatus().toString());
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

        bhapticsManager.scan();
    }
    public void AndroidThunkJava_StopScan() {
        bhapticsManager.stopScan();
    }

    public boolean AndroidThunkJava_IsScanning() {
        return bhapticsManager.isScanning();
    }

    // Device Settings
    public void AndroidThunkJava_Pair(String address) {
        bhapticsManager.pair(address);
    }

    // Pair Device with positoin
    public void AndroidThunkJava_PairFromPosition(String address, String position) {
        bhapticsManager.pair(address, com.bhaptics.commons.model.PositionType.valueOf(position));
    }

    public void AndroidThunkJava_Unpair(String address) {
        bhapticsManager.unpair(address);
    }

    public void AndroidThunkJava_UnpairAll() {
        bhapticsManager.unpairAll();
    }

    public void AndroidThunkJava_ChangePosition(String address, String position){
        bhapticsManager.changePosition(address, com.bhaptics.commons.model.PositionType.valueOf(position));
    }

    public void AndroidThunkJava_TogglePosition(String address) {
        bhapticsManager.togglePosition(address);
    }

    public void AndroidThunkJava_Ping(String address) {
        bhapticsManager.ping(address);
    }
    public void AndroidThunkJava_PingAll() {
        bhapticsManager.pingAll();
    }

    public String AndroidThunkJava_GetDeviceList() {
        List<BhapticsDevice> deviceList = bhapticsManager.getDeviceList();
        return DeviceToJsonString(deviceList);
    }

    public boolean AndroidThunkJava_GetLatestScanStatus() {
        return bhapticsManager.isScanning();
    }

    public void AndroidThunkJava_SubmitRegistered(String key, String altKey,
                                                  float intensity, float duration,
                                                  float offsetAngleX, float offsetY) {
        player.submitRegistered(key, altKey, intensity, duration, offsetAngleX, offsetY);
    }

    void  AndroidThunkJava_SubmitDot(String key, String position, int[] indexes, int[] intensities, int duration) {
        player.submitDot(key, position, indexes, intensities, duration);
    }
    void  AndroidThunkJava_SubmitPath(String key, String position, float[] x, float[] y, int[] intensities, int duration) {
        player.submitPath(key, position, x, y, intensities, duration);
    }

    public void AndroidThunkJava_Register(String key, String fileStr) {
        player.registerProject(key, fileStr);
    }
    public void AndroidThunkJava_RegisterReflected(String key, String fileStr) {
        player.registerProjectReflected(key, fileStr);
    }
    public void AndroidThunkJava_TurnOff(String key) {
        player.turnOff(key);
    }

    public void AndroidThunkJava_TurnOffAll() {
        player.turnOffAll();
    }

    public byte[] AndroidThunkJava_GetPositionStatus(String position) {
        return  player.getPositionStatus(PositionType.valueOf(position));
    }

    public boolean AndroidThunkJava_IsRegistered(String key) {
        return player.isRegistered(key);
    }
    public boolean AndroidThunkJava_IsPlaying(String key) {
        return player.isPlaying(key);
    }
    public boolean AndroidThunkJava_IsAnythingPlaying() {
        return player.isAnythingPlaying();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        player = new com.bhaptics.bhapticsmanger.HapticPlayerImpl();
        bhapticsManager = new com.bhaptics.bhapticsmanger.BhapticsManagerImpl(this, player, new com.bhaptics.ble.PairedDeviceManagerImpl(this));
        bhapticsManager.addBhapticsManageCallback(new com.bhaptics.bhapticsmanger.BhapticsManagerCallback() {
            @Override
            public void onDeviceUpdate(List<com.bhaptics.commons.model.BhapticsDevice> deviceList) {
                LatestDeviceStatus = DeviceToJsonString(deviceList);
                nativeOnDeviceFound(LatestDeviceStatus);
            }

            @Override
            public void onScanStatusChange(boolean scanning) {
                nativeOnChangeScanState();
            }

            @Override
            public void onChangeResponse() {
                nativeOnChangeResponse();
            }

            @Override
            public void onConnect(String address) {
                android.util.Log.i("MAIN_ACTIVITY", "onConnect: " + address );
            }
            @Override
            public void onDisconnect(String address) {
                android.util.Log.i("MAIN ACTIVITY", "onDisconnect: " + address );
            }
        });

    }
}
