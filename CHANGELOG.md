## Change Log

### 2.1.0
* Added `BhapticsPlayer.kt` Kotlin wrapper in `sample1`: keeps `appId`, `BhapticsPosition` enum, named / default arguments, Kotlin-typed device list.
* `sample1` now uses the wrapper.

### 2.0.0
* Switched to bHaptics Player for Android (AIDL service) mode. The SDK no longer connects to devices over Bluetooth directly.
* `libs/` now ships a single `bhaptics_manager.aar`. `bhaptics_ble.aar`, `bhaptics_commons.aar` and `bhaptics_core.aar` are removed.
* Bluetooth and location permissions are no longer needed. Add `<queries>` for `com.bhaptics.player` instead.
* `sample1` rewritten in Kotlin + Jetpack Compose: device list, `playMotors` for left/right TactSleeve and TactSuit, Developer Portal event playback.
* Build updated to AGP 8.12 / Gradle 8.13 / Kotlin 2.3 / compileSdk 36 / minSdk 24.
