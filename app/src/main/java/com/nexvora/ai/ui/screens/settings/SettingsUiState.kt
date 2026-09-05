package com.nexvora.ai.ui.screens.settings

import com.nexvora.ai.domain.model.AiModel
import com.nexvora.ai.domain.model.ThemeMode

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val selectedModel: AiModel,
    val cpuThreads: Int = 4,
    val contextSize: Int = 4096,
    val gpuAcceleration: Boolean = true,
    val storageUsage: String = "3.8 MB",
    val statusNotification: String? = null
)
