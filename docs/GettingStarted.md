## How to setup in your project

### Download and extract aar files
* You can download aar files [here](https://github.com/bhaptics/tact-android/releases)
* Then extract aar files to your app/libs files

### Import aar files to your project.
* open up your (app/build.gradle)
* Add aar file dependency like below
```

dependencies {
...
  // Assuming you put aar files to app/libs folder your  
  implementation fileTree(dir: 'libs', include: ['*.aar'])
}
```

### Update AndroidManifest.xml (app/src/main/AndroidManifest.xml)
* give permission for the bluetooth connection
```
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    
    <!-- If you want bHaptics Player's pairing information please add this -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```


## User Guide
### Initialize BhapticsModule
```
 BhapticsModule.initialize(getApplicationContext());
```

### Access BhapticsManager and HapticPlayer
```
// BhapticsManager is use to Manage pairing and connection ot bHaptics devices.
BhapticsManager bhapticsManager = BhapticsModule.getBhapticsManager();

// HapticPlayer is used to generate haptic patterns in various ways
HapticPlayer hapticPlayer = BhapticsModule.getHapticPlayer();
```

### Dispose when application ends
```
BhapticsModule.destroy();
```


### Check for permission and request permission.
* [About android permission](https://developer.android.com/training/permissions/requesting)
* Check and request location permission (to connect bHaptics devices)
```
if (!hasPermissions(this,
        Manifest.permission.ACCESS_FINE_LOCATION)) {
    // Permission is not granted
    Log.e(TAG, "onResume: permission ACCESS_FINE_LOCATION"  );
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            LOCATION_REQUEST_ID);
} else {
    // if you have permission then SDK will work as expected.
    
    // By scanning, paired devices will be connected automatically.
    bhapticsManager.scan();
}
```

* Optional: File permission to share pairing information
```
if (!hasPermissions(this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,},
            FILE_REQUEST_ID);
}
```


### How to generate haptic patterns
* [Guide](./HapticPlayer.md)

### How to manage connections
* [Guide](./BhapticsManager.md)
