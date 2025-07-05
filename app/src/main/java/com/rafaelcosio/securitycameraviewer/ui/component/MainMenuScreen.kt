package com.rafaelcosio.securitycameraviewer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainMenuScreen(
    cameras: List<CameraEntity>,
    onSelectCamera: (CameraEntity) -> Unit,
    onScanClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val cards = remember(cameras) {
        cameras.map {
            MenuItemData(
                label = it.name,
                icon = Icons.Default.Videocam,
                onClick = { onSelectCamera(it) }
            )
        } + listOf(
            MenuItemData("Escanear cámaras", Icons.Default.Wifi, onScanClick),
            MenuItemData("Configuración", Icons.Default.Settings, onSettingsClick)
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        Text(
            text = "Menú principal",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TvLazyVerticalGrid(
            columns = TvGridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { item ->
                MenuCard(item)
            }
        }
    }
}
