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
    private String appName = "";
    private static final String TAG = "bhaptics_WRAPPER";

    //Helper Functions
    private static String DeviceToJsonString(List<SimpleBhapticsDevice> devices) {
        try {

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
        } catch (Exception e) {
            android.util.Log.e(TAG, "DeviceToJsonString: " + e.getMessage(), e);
        }

        return "[]";
    }

    private static JSONObject DeviceToJsonObject(SimpleBhapticsDevice device) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("DeviceName", device.getDeviceName());
            obj.put("Address", device.getAddress());
            obj.put("Position", SimpleBhapticsDevice.positionToString(device.getPosition()));
            obj.put("IsConnected", device.isConnected());
            obj.put("IsPaired", device.isPaired());
            return obj;
        } catch (JSONException e) {
            android.util.Log.d(TAG, "toJsonObject: " + e.getMessage());
        }
        return null;
    }

    //Bluetooth Functionality
    public boolean AndroidThunkJava_Is_legacy() {
        return sdkRequestHandler.isLegacyMode();
    }
    public void AndroidThunkJava_Initialize(String appName) {
        android.util.Log.d(TAG, "AndroidThunkJava_Initialize: " + appName);
        this.appName = appName;
    }

    public String AndroidThunkJava_getDeviceList() {
        android.util.Log.d(TAG, "AndroidThunkJava_getDeviceList() ");
        return DeviceToJsonString(sdkRequestHandler.getDeviceList());
    }

    public void AndroidThunkJava_ChangePosition(String address, String position){
        android.util.Log.d(TAG, "AndroidThunkJava_ChangePosition() " + address + ", " + position);
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
        android.util.Log.d(TAG, "AndroidThunkJava_TogglePosition() " + address);
        sdkRequestHandler.togglePosition(address);
    }

    public void AndroidThunkJava_Ping(String address) {
        android.util.Log.d(TAG, "AndroidThunkJava_Ping() " + address);
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
        android.util.Log.d(TAG, "AndroidThunkJava_GetDeviceList() ");
        List<SimpleBhapticsDevice> deviceList = sdkRequestHandler.getDeviceList();
        return DeviceToJsonString(deviceList);
    }

    public void AndroidThunkJava_SubmitRegistered(String key, String altKey,
                                                  float intensity, float duration,
                                                  float offsetAngleX, float offsetY) {
        android.util.Log.d(TAG, "AndroidThunkJava_SubmitRegistered() ");
        sdkRequestHandler.submitRegistered(key, altKey, intensity, duration, offsetAngleX, offsetY);
    }

    void  AndroidThunkJava_SubmitDot(String key, String position, int[] indexes, int[] intensities, int duration) {
        android.util.Log.d(TAG, "AndroidThunkJava_SubmitDot() ");
        sdkRequestHandler.submitDot(key, position, indexes, intensities, duration);
    }
    void  AndroidThunkJava_SubmitPath(String key, String position, float[] x, float[] y, int[] intensities, int duration) {
        android.util.Log.d(TAG, "AndroidThunkJava_SubmitPath() ");
        sdkRequestHandler.submitPath(key, position, x, y, intensities, duration);
    }

    public void AndroidThunkJava_Register(String key, String fileStr) {
        android.util.Log.d(TAG, "AndroidThunkJava_Register() " + key);
        sdkRequestHandler.register(key, fileStr);
    }
    public void AndroidThunkJava_RegisterReflected(String key, String fileStr) {
        android.util.Log.d(TAG, "AndroidThunkJava_RegisterReflected() " + key);
        sdkRequestHandler.registerReflected(key, fileStr);
    }
    public void AndroidThunkJava_TurnOff(String key) {
        sdkRequestHandler.turnOff(key);
    }

    public void AndroidThunkJava_TurnOffAll() {
        sdkRequestHandler.turnOffAll();
    }

    public byte[] AndroidThunkJava_GetPositionStatus(String position) {
        android.util.Log.d(TAG, "AndroidThunkJava_GetPositionStatus() " + position);
        if (sdkRequestHandler == null) {
            android.util.Log.d(TAG, "AndroidThunkJava_GetPositionStatus() null" );
            return new byte[20];
        }
        return  sdkRequestHandler.getPositionStatus(position);
    }

    public boolean AndroidThunkJava_IsRegistered(String key) {
        android.util.Log.d(TAG, "AndroidThunkJava_IsRegistered() " + key );
        return sdkRequestHandler.isRegistered(key);
    }
    public boolean AndroidThunkJava_IsPlaying(String key) {
        android.util.Log.d(TAG, "AndroidThunkJava_IsPlaying() " + key );
        return sdkRequestHandler.isPlaying(key);
    }
    public boolean AndroidThunkJava_IsAnythingPlaying() {
        android.util.Log.d(TAG, "AndroidThunkJava_IsAnythingPlaying() " );
        return sdkRequestHandler.isAnythingPlaying();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sdkRequestHandler = new SdkRequestHandler(this, "appName");
    }
}
