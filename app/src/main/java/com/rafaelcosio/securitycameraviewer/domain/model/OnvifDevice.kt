package com.rafaelcosio.securitycameraviewer.domain.model

data class OnvifDevice(
    val xAddrs: List<String>, // Endpoints del servicio ONVIF
    val scopes: List<String>, // Información adicional, a veces incluye nombre, ubicación
    val address: String       // Dirección IP del dispositivo
)