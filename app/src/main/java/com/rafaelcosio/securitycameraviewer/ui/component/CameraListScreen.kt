package com.rafaelcosio.securitycameraviewer.ui.component

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.rafaelcosio.securitycameraviewer.ui.viewmodel.CamerasViewModel

@OptIn(UnstableApi::class)
@Composable
fun CameraListScreen(viewModel: CamerasViewModel = hiltViewModel()) {
    val cameraIps by viewModel.cameraIps.collectAsState()
    var showStreamUrl by remember { mutableStateOf<String?>(null) } // State to hold the stream URL

    // Test RTSP URL
    val username = "admin"
    val password = "IB7Eulpt"
    val cameraIp = "192.168.1.122"
    val cameraPort = "554"
    val channel = "0"
    val streamQuality = "0" // 0 = Main Stream, 1 = Sub Stream

    val fullRtspUrl = "rtsp://${cameraIp}:${cameraPort}/user=${username}_password=${password}_channel=${channel}_stream=:${streamQuality}&onvif=0.sdp?real_stream"

    Log.d("CameraApp", "Intentando RTSP URL: $fullRtspUrl")

    /*
    Column {
        Button(onClick = {
            //viewModel.scanCameras()
            showStreamUrl = fullRtspUrl
        }) {
            Text("Escanear Cámaras")
        }

        showStreamUrl?.let { url ->
            CameraStreamView(url, true)
        }

        LazyColumn {
            items(cameraIps) { ip ->
                Text(text = "Cámara: $ip")
                CameraStreamView(ip.address, true)
            }
        }
    }

     */
}