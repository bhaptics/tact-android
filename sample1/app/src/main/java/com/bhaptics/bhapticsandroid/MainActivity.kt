package com.bhaptics.bhapticsandroid

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var player: BhapticsPlayer? = null
    private val logs = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )

        setContent {
            MaterialTheme {
                SampleScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.quit()
    }

    private fun withPlayer(action: BhapticsPlayer.() -> Unit) {
        player?.action() ?: log("Initialize first")
    }

    @Composable
    private fun SampleScreen() {
        var appId by remember { mutableStateOf("") }
        var apiKey by remember { mutableStateOf("") }
        var intensity by remember { mutableFloatStateOf(100f) }
        var duration by remember { mutableStateOf("300") }
        var event by remember { mutableStateOf("") }
        val listState = rememberLazyListState()

        LaunchedEffect(logs.size) {
            if (logs.isNotEmpty()) listState.animateScrollToItem(logs.lastIndex)
        }

        fun playMotors(position: BhapticsPosition) = withPlayer {
            val millis = duration.toIntOrNull() ?: 300
            val result = playMotors(position, millis, intensity.toInt())
            log("playMotors($position, ${millis}ms, ${intensity.toInt()}) -> $result")
        }

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(appId, { appId = it }, Modifier.fillMaxWidth(), label = { Text("App ID") }, singleLine = true)
                OutlinedTextField(apiKey, { apiKey = it }, Modifier.fillMaxWidth(), label = { Text("API Key") }, singleLine = true)

                ButtonRow {
                    RowButton("Initialize") {
                        player?.quit()
                        player = BhapticsPlayer(this@MainActivity, appId, apiKey).also {
                            log("initialize($appId) installed=${it.isPlayerInstalled}")
                        }
                    }
                    RowButton("Devices") {
                        withPlayer {
                            log("${devices.size} device(s)")
                            devices.forEach { log("  ${it.position} connected=${it.isConnected} battery=${it.battery}") }
                        }
                    }
                    RowButton("Ping all") {
                        withPlayer {
                            pingAll()
                            log("ping all")
                        }
                    }
                }

                Text("Intensity: ${intensity.toInt()}")
                Slider(intensity, { intensity = it }, valueRange = 0f..100f)
                OutlinedTextField(
                    duration,
                    { duration = it },
                    Modifier.fillMaxWidth(),
                    label = { Text("Duration (ms)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                ButtonRow {
                    RowButton("Left") { playMotors(BhapticsPosition.ForearmL) }
                    RowButton("Right") { playMotors(BhapticsPosition.ForearmR) }
                    RowButton("Both") {
                        playMotors(BhapticsPosition.ForearmL)
                        playMotors(BhapticsPosition.ForearmR)
                    }
                    RowButton("Vest") { playMotors(BhapticsPosition.Vest) }
                }

                ButtonRow {
                    OutlinedTextField(event, { event = it }, Modifier.weight(2f), label = { Text("Event name") }, singleLine = true)
                    RowButton("Play event") {
                        withPlayer {
                            val result = play(event, intensity = intensity / 100f)
                            log("play($event, intensity=${intensity / 100f}) -> $result")
                        }
                    }
                }

                ButtonRow {
                    RowButton("Stop all") { withPlayer { log("stopAll -> ${stopAll()}") } }
                    RowButton("Clear log") { logs.clear() }
                }

                LazyColumn(Modifier.fillMaxWidth().weight(1f), state = listState) {
                    items(logs) { Text(it, fontFamily = FontFamily.Monospace, fontSize = 12.sp) }
                }
            }
        }
    }

    private fun log(message: String) {
        val time = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
        logs += "$time  $message"
    }
}

@Composable
private fun ButtonRow(content: @Composable RowScope.() -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), content = content)
}

@Composable
private fun RowScope.RowButton(text: String, onClick: () -> Unit) {
    Button(onClick, Modifier.weight(1f)) { Text(text) }
}
