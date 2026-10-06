package com.bhaptics.bhapticsandroid

import android.app.Activity
import com.bhaptics.bhapticsmanger.SdkRequestHandler
import com.bhaptics.service.SimpleBhapticsDevice

enum class BhapticsPosition(val value: Int, val motorCount: Int) {
    Vest(0, 40),
    ForearmL(1, 3),
    ForearmR(2, 3),
    Head(3, 4),
    HandL(4, 3),
    HandR(5, 3),
    FootL(6, 3),
    FootR(7, 3),
    GloveL(8, 8),
    GloveR(9, 8);

    companion object {
        fun from(value: Int): BhapticsPosition? = entries.firstOrNull { it.value == value }
    }
}

data class BhapticsDevice(
    val name: String,
    val address: String,
    val position: BhapticsPosition?,
    val isConnected: Boolean,
    val battery: Int,
)

class BhapticsPlayer(activity: Activity, val appId: String, apiKey: String = "") {

    private val sdk = SdkRequestHandler(activity).apply { initialize(appId, apiKey, "") }

    val isPlayerInstalled: Boolean
        get() = sdk.isBhapticsUser

    val devices: List<BhapticsDevice>
        get() = sdk.deviceList.map {
            BhapticsDevice(
                name = it.deviceName.orEmpty(),
                address = it.address.orEmpty(),
                position = BhapticsPosition.from(it.position),
                isConnected = it.isConnected,
                battery = it.battery,
            )
        }

    val isPlaying: Boolean
        get() = sdk.isAnythingPlaying

    fun playMotors(position: BhapticsPosition, durationMillis: Int, motors: IntArray): Int =
        sdk.playMotors(appId, position.value, durationMillis, motors)

    fun playMotors(position: BhapticsPosition, durationMillis: Int, motorValue: Int): Int =
        playMotors(position, durationMillis, IntArray(position.motorCount) { motorValue.coerceIn(0, 100) })

    fun play(
        event: String,
        intensityRatio: Float = 1f,
        durationRatio: Float = 1f,
        angleX: Float = 0f,
        offsetY: Float = 0f,
    ): Int = sdk.play(appId, event, intensityRatio, durationRatio, angleX, offsetY)

    fun stop(requestId: Int): Boolean = sdk.stop(appId, requestId)

    fun stop(event: String): Boolean = sdk.stopByEventId(appId, event)

    fun stopAll(): Boolean = sdk.stopAll(appId)

    fun ping(device: BhapticsDevice) = sdk.ping(device.address)

    fun pingAll() = devices.filter { it.isConnected }.forEach(::ping)

    fun quit() = sdk.quit()
}
