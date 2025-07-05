package com.rafaelcosio.securitycameraviewer.ui.component

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
