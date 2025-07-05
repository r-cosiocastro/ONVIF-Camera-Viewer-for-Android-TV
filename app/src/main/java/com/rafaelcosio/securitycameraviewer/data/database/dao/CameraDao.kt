package com.rafaelcosio.securitycameraviewer.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CameraDao {
    @Query("SELECT * FROM cameras ORDER BY id DESC")
    fun getAllCameras(): Flow<List<CameraEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(camera: CameraEntity)

    @Delete
    suspend fun delete(camera: CameraEntity)
}