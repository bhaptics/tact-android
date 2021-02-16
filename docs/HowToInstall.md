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
