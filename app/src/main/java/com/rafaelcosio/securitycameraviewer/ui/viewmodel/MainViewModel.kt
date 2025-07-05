package com.rafaelcosio.securitycameraviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelcosio.securitycameraviewer.data.repository.CameraRepository
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CameraRepository
) : ViewModel() {
    private val _cameras = MutableStateFlow<List<CameraEntity>>(emptyList())
    val cameras: StateFlow<List<CameraEntity>> = _cameras.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllCameras().collect { _cameras.value = it }
        }
    }

    fun addCamera(camera: CameraEntity) {
        viewModelScope.launch {
            repository.insertCamera(camera)
        }
    }

    fun deleteCamera(camera: CameraEntity) {
        viewModelScope.launch {
            repository.deleteCamera(camera)
        }
    }
}
