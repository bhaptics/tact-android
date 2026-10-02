package com.bhaptics.bhapticsandroid

import com.bhaptics.service.SimpleBhapticsDevice
import org.junit.Assert.assertEquals
import org.junit.Test

class BhapticsPositionTest {
    @Test
    fun matchesSdkPositionValues() {
        BhapticsPosition.entries.forEach {
            assertEquals(it.name, SimpleBhapticsDevice.positionToString(it.value))
            assertEquals(it, BhapticsPosition.from(SimpleBhapticsDevice.stringToPosition(it.name)))
        }
    }
}
