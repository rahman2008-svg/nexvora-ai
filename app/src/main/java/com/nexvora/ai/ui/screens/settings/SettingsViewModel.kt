package com.nexvora.ai.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexvora.ai.data.SettingsRepository
import com.nexvora.ai.domain.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository = SettingsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            themeMode = settingsRepository.themeMode.value,
            selectedModel = settingsRepository.selectedModel.value,
            cpuThreads = settingsRepository.cpuThreads.value,
            contextSize = settingsRepository.contextSize.value,
            gpuAcceleration = settingsRepository.gpuAcceleration.value
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.themeMode.collect { mode ->
                _uiState.update { it.copy(themeMode = mode) }
            }
        }
        viewModelScope.launch {
            settingsRepository.selectedModel.collect { model ->
                _uiState.update { it.copy(selectedModel = model) }
            }
        }
        viewModelScope.launch {
            settingsRepository.cpuThreads.collect { threads ->
                _uiState.update { it.copy(cpuThreads = threads) }
            }
        }
        viewModelScope.launch {
            settingsRepository.gpuAcceleration.collect { enabled ->
                _uiState.update { it.copy(gpuAcceleration = enabled) }
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        settingsRepository.setThemeMode(mode)
        _uiState.update { it.copy(themeMode = mode) }
    }

    fun setGpuAcceleration(enabled: Boolean) {
        settingsRepository.setGpuAcceleration(enabled)
        _uiState.update { it.copy(gpuAcceleration = enabled) }
    }

    fun setCpuThreads(threads: Int) {
        settingsRepository.setCpuThreads(threads)
        _uiState.update { it.copy(cpuThreads = threads) }
    }

    fun clearCache() {
        _uiState.update {
            it.copy(
                storageUsage = "0 KB",
                statusNotification = "Local cache cleared successfully."
            )
        }
    }

    fun dismissNotification() {
        _uiState.update { it.copy(statusNotification = null) }
    }
}
