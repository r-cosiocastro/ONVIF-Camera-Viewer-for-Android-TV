package com.rafaelcosio.securitycameraviewer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelcosio.securitycameraviewer.data.scanner.CameraScanner
import com.rafaelcosio.securitycameraviewer.domain.model.OnvifDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamerasViewModel @Inject constructor(
    private val scanner: CameraScanner
) : ViewModel() {

    private val _cameraIps = MutableStateFlow<List<OnvifDevice>>(emptyList())
    val cameraIps: StateFlow<List<OnvifDevice>> = _cameraIps

    fun scanCameras() {
        viewModelScope.launch {
            Log.d("CamerasViewModel", "Starting camera scan")
            val ips = scanner.scanForOnvifDevices()
            _cameraIps.value = ips
        }
    }
}