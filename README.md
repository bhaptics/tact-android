## bHaptics Android SDK example project

Android library and sample app for playing haptics on bHaptics devices (TactSuit, TactSleeve, TactGlove, TactVisor, ...) from your own Android app.

The SDK talks to the **bHaptics Player for Android** app over AIDL. The Player app handles the Bluetooth connection to the devices, so your app does not need any Bluetooth code or permissions, and no PC is required.

```
Your app ──(SdkRequestHandler / AIDL)──▶ bHaptics Player for Android ──(BLE)──▶ bHaptics devices
```

### Requirements
* Android 7.0 (API 24) or later
* [bHaptics Player for Android](https://play.google.com/store/apps/details?id=com.bhaptics.player&hl=en) installed, with your devices paired in it
* An App ID and API Key from the [bHaptics Developer Portal](https://developer.bhaptics.com) (needed for Developer Portal events)

### Documentation
* [How to Install](docs/HowToInstall.md)
* [Getting Started](docs/GettingStarted.md): API usage examples
* [Change log](CHANGELOG.md)

### Sample app
`sample1` is a minimal app that initializes the SDK, lists devices, and plays the left/right TactSleeve and TactSuit with a chosen intensity and duration.

```
cd sample1
./gradlew :app:installDebug
```

### License
```
Copyright 2015~2026 bHaptics Inc.
```
