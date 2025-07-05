package com.rafaelcosio.securitycameraviewer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cameras")
data class CameraEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val ip: String,
    val user: String?,
    val password: String?,
    val info: String // Ej: "H264 IPC_NT98566_IPG-N2-WE2_S38"
)
