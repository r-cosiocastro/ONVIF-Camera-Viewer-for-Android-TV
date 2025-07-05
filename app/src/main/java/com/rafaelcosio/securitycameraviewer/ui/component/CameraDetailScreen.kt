package com.rafaelcosio.securitycameraviewer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CameraDetailScreen(camera: CameraEntity, onOptionSelected: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        // Panel izquierdo
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(text = camera.ip, style = MaterialTheme.typography.bodyLarge)
            Text(text = camera.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = camera.info, style = MaterialTheme.typography.bodyMedium)
        }

        // Panel derecho
        Column(
            modifier = Modifier
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            listOf("Vista en vivo", "Configuraciones", "Eventos").forEach { option ->
                Text(
                    text = option,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusable()
                        .clickable { onOptionSelected(option) },
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
