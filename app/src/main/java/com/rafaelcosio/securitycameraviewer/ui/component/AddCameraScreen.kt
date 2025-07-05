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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AddCameraScreen(
    discoveredCameras: List<String>, // Ej: "192.168.1.64"
    selectedCameraIp: String?,
    onSelectCamera: (String) -> Unit,
    onAddCamera: (CameraEntity) -> Unit
) {
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
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(text = "Agregar cámara", style = MaterialTheme.typography.headlineSmall)
            selectedCameraIp?.let {
                Text(text = it, style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Panel derecho
        Column(
            modifier = Modifier
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(24.dp)
        ) {
            if (selectedCameraIp == null) {
                // Mostrar lista de IPs descubiertas
                discoveredCameras.forEach { ip ->
                    Text(
                        text = "$ip\nRTSP",
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusable()
                            .clickable { onSelectCamera(ip) },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                // Mostrar formulario
                var name by remember { mutableStateOf("") }
                var user by remember { mutableStateOf("") }
                var pass by remember { mutableStateOf("") }
                var showPassword by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de cámara") })
                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Usuario") })
                OutlinedTextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Contraseña") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                )
                Button(onClick = { showPassword = !showPassword }) {
                    Text(text = if (showPassword) "Ocultar" else "Mostrar contraseña")
                }
                Button(onClick = {
                    onAddCamera(
                        CameraEntity(
                            name = name,
                            ip = selectedCameraIp,
                            user = user,
                            password = pass,
                            info = ""
                        )
                    )
                }) {
                    Text("Agregar cámara")
                }
            }
        }
    }
}
