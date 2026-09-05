package com.nexvora.ai

import com.nexvora.ai.data.SettingsRepository
import com.nexvora.ai.domain.model.ThemeMode
import com.nexvora.ai.ui.screens.settings.SettingsViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        settingsRepository = SettingsRepository()
        viewModel = SettingsViewModel(settingsRepository)
    }

    @Test
    fun `theme mode toggle updates state`() {
        assertEquals(ThemeMode.SYSTEM, viewModel.uiState.value.themeMode)
        viewModel.setThemeMode(ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, viewModel.uiState.value.themeMode)
        viewModel.setThemeMode(ThemeMode.LIGHT)
        assertEquals(ThemeMode.LIGHT, viewModel.uiState.value.themeMode)
    }

    @Test
    fun `clear cache sets storage to 0 and notifies`() {
        viewModel.clearCache()
        assertEquals("0 KB", viewModel.uiState.value.storageUsage)
        assertNotNull(viewModel.uiState.value.statusNotification)
    }

    @Test
    fun `gpu acceleration toggle updates state`() {
        viewModel.setGpuAcceleration(false)
        assertEquals(false, viewModel.uiState.value.gpuAcceleration)
        viewModel.setGpuAcceleration(true)
        assertEquals(true, viewModel.uiState.value.gpuAcceleration)
    }
}
