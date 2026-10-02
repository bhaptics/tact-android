## How to set up your project

### 1. Install bHaptics Player for Android
* Install [bHaptics Player for Android](https://play.google.com/store/apps/details?id=com.bhaptics.player) on the phone.
* Pair your devices in the Player app.

### 2. Add the aar file
* Copy [`libs/bhaptics_manager.aar`](../libs/bhaptics_manager.aar) to your `app/libs` folder.
* Add the dependency in `app/build.gradle`:
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.aar'])
}
```

### 3. Update AndroidManifest.xml
* On Android 11 (API 30) and later, declare the Player app package so your app can bind to it:
```
<manifest ...>
    <queries>
        <package android:name="com.bhaptics.player" />
    </queries>
    ...
</manifest>
```
* No Bluetooth or location permission is needed. The Player app owns the Bluetooth connection.
