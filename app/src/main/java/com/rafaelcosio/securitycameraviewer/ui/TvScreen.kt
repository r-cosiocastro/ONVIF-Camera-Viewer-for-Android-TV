package com.rafaelcosio.securitycameraviewer.ui

import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity

sealed class TvScreen {
    object MainMenu : TvScreen()
    data class CameraDetail(val camera: CameraEntity) : TvScreen()
    object Scan : TvScreen()
    object Settings : TvScreen()
}
