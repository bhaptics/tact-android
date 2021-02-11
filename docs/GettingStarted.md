## How to setup in your project

### Download aar files
* You can download aar files [here](https://github.com/bhaptics/tact-android/releases)

### Import aar files to your project.
* open up your (app/build.gradle)
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
### Check for permission and request permission.

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

* scan??

### Initialize and Get hapticplayer
```
NativeHapticPlayer hapticPlayer = NativeHapticPlayer.getInstance(this);
```


### Use HapticPlayer

```
// register tact file (VestDemoActivity)
 hapticPlayer.registerProject(fileKey, fileContent);

// play tact file (VestDemoActivity)
hapticPlayer.submit(fileKey);

// to turnOff
hapticPlayer.turnOff(fileKey);
hapticPlayer.turnOffAll();
```