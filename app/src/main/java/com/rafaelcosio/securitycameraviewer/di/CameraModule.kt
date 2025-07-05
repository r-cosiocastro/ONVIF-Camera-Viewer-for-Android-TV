package com.rafaelcosio.securitycameraviewer.di

import android.content.Context
import androidx.room.Room
import com.rafaelcosio.securitycameraviewer.data.database.CameraDatabase
import com.rafaelcosio.securitycameraviewer.data.database.dao.CameraDao
import com.rafaelcosio.securitycameraviewer.data.repository.CameraRepository
import com.rafaelcosio.securitycameraviewer.data.repository.CameraRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CameraDatabase {
        return Room.databaseBuilder(
            context,
            CameraDatabase::class.java,
            "camera_db"
        ).build()
    }

    @Provides
    fun provideCameraDao(db: CameraDatabase): CameraDao = db.cameraDao()

    @Provides
    fun provideCameraRepository(dao: CameraDao): CameraRepository =
        CameraRepositoryImpl(dao)
}
