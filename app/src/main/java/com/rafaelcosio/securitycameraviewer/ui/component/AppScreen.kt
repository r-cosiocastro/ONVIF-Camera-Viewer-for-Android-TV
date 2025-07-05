package com.rafaelcosio.securitycameraviewer.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafaelcosio.securitycameraviewer.ui.TvScreen
import com.rafaelcosio.securitycameraviewer.ui.viewmodel.MainViewModel

@Composable
fun AppScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    var currentScreen by remember { mutableStateOf<TvScreen>(TvScreen.MainMenu) }

    if (currentScreen != TvScreen.MainMenu) {
        BackHandler {
            currentScreen = TvScreen.MainMenu
        }
    }

    when (val screen = currentScreen) {
        is TvScreen.MainMenu -> MainMenuScreen(
            cameras = mainViewModel.cameras.collectAsState().value,
            onSelectCamera = { camera -> currentScreen = TvScreen.CameraDetail(camera) },
            onScanClick = { currentScreen = TvScreen.Scan },
            onSettingsClick = { currentScreen = TvScreen.Settings }
        )

        is TvScreen.CameraDetail -> CameraDetailScreen(
            camera = screen.camera,
            onOptionSelected = { option ->
                when (option) {
                    "Configuraciones" -> { /* TODO: Ir a config individual */
                    }

                    "Vista en vivo" -> { /* TODO: Ir a stream */
                    }

                    "Eventos" -> { /* TODO: Ir a eventos */
                    }
                }
            }
        )

        is TvScreen.Scan -> AddCameraScreen(
            discoveredCameras = listOf("192.168.1.64", "192.168.1.119"), // temporal
            selectedCameraIp = null, // estado local
            onSelectCamera = { ip -> /* TODO: manejar selección */ },
            onAddCamera = { camera ->
                mainViewModel.addCamera(camera)
                currentScreen = TvScreen.MainMenu
            }
        )

        is TvScreen.Settings -> SettingsScreen(
            onBack = { currentScreen = TvScreen.MainMenu }
        )
    }
}
