package com.nexvora.ai.data

import com.nexvora.ai.domain.model.AiModel
import com.nexvora.ai.domain.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Local repository managing user settings and application configuration.
 */
class SettingsRepository {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _availableModels = MutableStateFlow(
        listOf(
            AiModel(
                id = "llama-3.2-3b-q4",
                name = "Llama 3.2 3B Instruct",
                parameterSize = "3.2B",
                quantization = "Q4_K_M",
                diskSize = "2.0 GB",
                description = "Balanced efficiency and reasoning for general local assistant tasks.",
                isRecommended = true,
                isAvailableOffline = false
            ),
            AiModel(
                id = "phi-3.5-mini-q4",
                name = "Phi-3.5 Mini 4K",
                parameterSize = "3.8B",
                quantization = "Q4_K_M",
                diskSize = "2.2 GB",
                description = "Strong analytical, coding, and mathematical reasoning in a compact footprint.",
                isRecommended = false,
                isAvailableOffline = false
            ),
            AiModel(
                id = "mistral-7b-q4",
                name = "Mistral 7B Instruct",
                parameterSize = "7.3B",
                quantization = "Q4_K_M",
                diskSize = "4.1 GB",
                description = "High capability model for in-depth writing and complex instructions.",
                isRecommended = false,
                isAvailableOffline = false
            )
        )
    )
    val availableModels: StateFlow<List<AiModel>> = _availableModels.asStateFlow()

    private val _selectedModel = MutableStateFlow(_availableModels.value.first())
    val selectedModel: StateFlow<AiModel> = _selectedModel.asStateFlow()

    private val _cpuThreads = MutableStateFlow(4)
    val cpuThreads: StateFlow<Int> = _cpuThreads.asStateFlow()

    private val _contextSize = MutableStateFlow(4096)
    val contextSize: StateFlow<Int> = _contextSize.asStateFlow()

    private val _gpuAcceleration = MutableStateFlow(true)
    val gpuAcceleration: StateFlow<Boolean> = _gpuAcceleration.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun setSelectedModel(model: AiModel) {
        _selectedModel.value = model
    }

    fun setCpuThreads(threads: Int) {
        _cpuThreads.value = threads
    }

    fun setGpuAcceleration(enabled: Boolean) {
        _gpuAcceleration.value = enabled
    }
}
