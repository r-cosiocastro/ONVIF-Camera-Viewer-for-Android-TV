package com.rafaelcosio.securitycameraviewer.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.SurfaceDefaults
import com.rafaelcosio.securitycameraviewer.ui.component.CameraListScreen
import com.rafaelcosio.securitycameraviewer.ui.theme.SecurityCameraViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecurityCameraViewerTheme { // Usa tu tema aquí, si lo tienes
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    colors = SurfaceDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    CameraListScreen() // Aquí se muestra tu Composable
                }
            }
        }
    }
}