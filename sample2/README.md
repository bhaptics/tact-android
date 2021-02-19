## How to - without pairing UI
### Download aar files and extract it to app/libs folder
* [Link](https://github.com/bhaptics/tact-android/releases/download/v1.10/tact-android-v1.10.zip)

### from app/build.gradle add line below
```
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
```

### Add permission to app\src\main\AndroidManifest.xml
```
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### Initializing and destroying (MainActivity.java)
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        BhapticsModule.initialize(this);
    }

    @Override
    protected void onDestroy() {
        ...
        BhapticsModule.destroy();
    }
```


### Check and Request Permission in the code. (MainActivity.java)
```java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        checkPermissionAndRequestIfNeeded();
    }
    
    private void checkPermissionAndRequestIfNeeded() {
        if(hasPermission()) {
            scanIfNeeded();
        } else {
            requestPermission();
        }
    }
    
    private boolean hasPermission() {
        boolean blePermission = PermissionUtils.hasBluetoothPermission(this);
        boolean filePermission = PermissionUtils.hasFilePermissions(this);
        return blePermission && filePermission;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,},
        1);
    }

    private void scanIfNeeded() {
        BhapticsManager manager = BhapticsModule.getBhapticsManager();

        List<BhapticsDevice> deviceList = manager.getDeviceList();
        boolean hasPairedDevice = false;
        for (BhapticsDevice device : deviceList) {
            if (device.isPaired()) {
                hasPairedDevice = true;
                break;
            }
        }

        if (hasPairedDevice) {
            manager.scan();
        }
    }

```


### Now Play Haptic
* For more example, please check this [doc](../docs/HapticPlayer.md)
```java
    private void playHaptic() {
        HapticPlayer player = BhapticsModule.getHapticPlayer();
        player.submitDot("play", PositionType.VestFront, Arrays.asList(new DotPoint(0, 100)), 1000);
    }
```
