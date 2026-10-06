## Getting Started

### Install bHaptics Player for Android
* Download [bHaptics Player for Android](https://play.google.com/store/apps/details?id=com.bhaptics.player&hl=en) from Google Play and pair your devices in it. The SDK plays haptics through this app.

### bHaptics Developer Portal
* To play haptic events, create an application and its events in the [bHaptics Developer Portal](https://developer.bhaptics.com) first.
* `playMotors` does not need any Developer Portal event.

### Kotlin wrapper
For Kotlin, copy [`BhapticsPlayer.kt`](../sample1/app/src/main/java/com/bhaptics/bhapticsandroid/BhapticsPlayer.kt) into your project (change the `package` line). It keeps `appId`, uses a `BhapticsPosition` enum, and supports named / default arguments.
```kotlin
val haptics = BhapticsPlayer(activity, appId = "your-app-id", apiKey = "your-api-key")

haptics.playMotors(BhapticsPosition.ForearmL, durationMillis = 300, motorValue = 80)
haptics.playMotors(BhapticsPosition.Vest, durationMillis = 300, motors = IntArray(32) { 50 })
haptics.play("DangerLeft", intensityRatio = 0.5f)
haptics.devices.filter { it.isConnected }.forEach { println("${it.position} ${it.battery}") }
haptics.stopAll()
haptics.quit()
```

The sections below use `SdkRequestHandler` directly (Java).

### Initialize
`SdkRequestHandler` binds to bHaptics Player for Android when it is created.
```java
SdkRequestHandler sdk = new SdkRequestHandler(activity);
sdk.initialize(appId, apiKey, "");
```
* `sdk.isBhapticsUser()` returns `false` if bHaptics Player for Android is not installed.

### Release when the app ends
```java
sdk.quit();
```

### Play motors directly
Set each motor's intensity (0–100) at runtime, without a Developer Portal event.
```java
int[] motors = {100, 100, 100};                  // TactSleeve has 3 motors
int requestId = sdk.playMotors(appId, 1, 300, motors);   // position 1 = ForearmL, 300 ms
```

| position | Device |
|---|---|
| 0 | TactSuit (Vest, 32 motors) |
| 1 | TactSleeve left (ForearmL, 3 motors) |
| 2 | TactSleeve right (ForearmR, 3 motors) |
| 3 | TactVisor (Head) |
| 4 / 5 | Hand left / right |
| 6 / 7 | Foot left / right |
| 8 / 9 | TactGlove left / right |

Motor index layout for each device: [Motor Index](https://docs.bhaptics.com/sdk/further/motor).

### Play a Developer Portal event
```java
sdk.play(appId, eventName);
sdk.play(appId, eventName, intensity, duration, angleX, offsetY);
```
| Parameter | Description |
|---|---|
| `intensity` | Intensity multiplier. `1` = as designed |
| `duration` | Duration multiplier. `1` = as designed |
| `angleX` | Rotates the pattern around the body horizontally (0–360). Used for TactSuit |
| `offsetY` | Moves the pattern up or down (-0.5–0.5). Used for TactSuit |

### Stop
```java
sdk.stopAll(appId);
```

### Devices
```java
for (SimpleBhapticsDevice device : sdk.getDeviceList()) {
    SimpleBhapticsDevice.positionToString(device.getPosition());
    device.isConnected();
    device.getBattery();
    sdk.ping(device.getAddress());
}
```
