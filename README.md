## bHaptics android library example project
### Example App Demo 
* Download app-release-unsigned.apk file (https://github.com/bhaptics/tact-android/releases)
* Video Link : https://youtu.be/NZHA9KCstEk

### How to use
1. put tact-release.aar (https://github.com/bhaptics/tact-android/releases) to your library folder(app/libs)

2. Add tact library in your project (app/build.gradle)
```
dependencies {
    api(name: 'tact-release', ext: 'aar')
}
```

3. AndroidManifest.xml (app/src/main/AndroidManifest.xml)
* give permission for the bluetooth connection
```
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```


4. How to user NativeHapticPlayer in Activity (LobbyActivity.java)
```
NativeHapticPlayer hapticPlayer = NativeHapticPlayer.getInstance(this);
```

* start after getting permissions
```
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
```

5. Use NativeHapticPlayer

```java
// register tact file (VestDemoActivity)
 hapticPlayer.register(fileKey, fileContent);

// play tact file (VestDemoActivity)
hapticPlayer.submit(fileKey);

// to turnOff
hapticPlayer.turnOff(fileKey);
hapticPlayer.turnOffAll();
```
