package com.rafaelcosio.securitycameraviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelcosio.securitycameraviewer.data.database.dao.CameraDao
import com.rafaelcosio.securitycameraviewer.domain.model.CameraEntity

@Database(entities = [CameraEntity::class], version = 1, exportSchema = false)
abstract class CameraDatabase : RoomDatabase() {
    abstract fun cameraDao(): CameraDao
}