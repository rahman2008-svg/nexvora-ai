package com.nexvora.ai

import com.nexvora.ai.data.SettingsRepository
import com.nexvora.ai.ui.screens.chat.ChatViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChatViewModelTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var viewModel: ChatViewModel

    @Before
    fun setUp() {
        settingsRepository = SettingsRepository()
        viewModel = ChatViewModel(settingsRepository)
    }

    @Test
    fun `initial state has empty input and suggestions`() {
        val state = viewModel.uiState.value
        assertEquals("", state.inputText)
        assertFalse(state.isSendEnabled)
        assertEquals(4, state.suggestions.size)
        assertNotNull(state.selectedModel)
    }

    @Test
    fun `input text updates correctly`() {
        viewModel.onInputTextChange("Test prompt")
        val state = viewModel.uiState.value
        assertEquals("Test prompt", state.inputText)
        assertTrue(state.isSendEnabled)

        viewModel.onClearInput()
        assertEquals("", viewModel.uiState.value.inputText)
        assertFalse(viewModel.uiState.value.isSendEnabled)
    }

    @Test
    fun `selecting suggestion populates input text`() {
        val suggestion = viewModel.uiState.value.suggestions.first()
        viewModel.onSuggestionSelected(suggestion)
        assertEquals(suggestion.promptText, viewModel.uiState.value.inputText)
        assertTrue(viewModel.uiState.value.isSendEnabled)
    }

    @Test
    fun `send click sets Step 1 informational notice`() {
        viewModel.onInputTextChange("Hello NexVora")
        viewModel.onSendClicked()
        assertNotNull(viewModel.uiState.value.infoMessage)
        assertTrue(viewModel.uiState.value.infoMessage!!.contains("Step 1 Foundation"))
    }
}
