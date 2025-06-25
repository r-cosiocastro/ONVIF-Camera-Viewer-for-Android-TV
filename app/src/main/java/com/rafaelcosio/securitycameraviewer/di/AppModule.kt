package com.rafaelcosio.securitycameraviewer.di

import com.rafaelcosio.securitycameraviewer.data.scanner.CameraScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideCameraScanner(): CameraScanner {
        return CameraScanner()
    }
}