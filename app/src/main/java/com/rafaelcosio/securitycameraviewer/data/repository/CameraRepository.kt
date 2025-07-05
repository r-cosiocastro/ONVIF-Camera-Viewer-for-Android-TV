package com.rafaelcosio.securitycameraviewer.data.repository

import com.rafaelcosio.securitycameraviewer.data.database.dao.CameraDao
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CameraRepository {
    fun getAllCameras(): Flow<List<CameraEntity>>
    suspend fun insertCamera(camera: CameraEntity)
    suspend fun deleteCamera(camera: CameraEntity)
}

class CameraRepositoryImpl @Inject constructor(
    private val dao: CameraDao
) : CameraRepository {
    override fun getAllCameras(): Flow<List<CameraEntity>> = dao.getAllCameras()

    override suspend fun insertCamera(camera: CameraEntity) {
        dao.insert(camera)
    }

    override suspend fun deleteCamera(camera: CameraEntity) {
        dao.delete(camera)
    }
}
