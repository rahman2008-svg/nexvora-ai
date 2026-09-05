package com.nexvora.ai.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexvora.ai.data.SettingsRepository
import com.nexvora.ai.domain.model.AiModel
import com.nexvora.ai.domain.model.SuggestionPrompt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val settingsRepository: SettingsRepository = SettingsRepository()
) : ViewModel() {

    private val defaultSuggestions = listOf(
        SuggestionPrompt(
            id = "explain",
            title = "Explain",
            subtitle = "Explain complex concepts step-by-step",
            promptText = "Explain how transformer neural networks and local LLM quantization work in simple terms."
        ),
        SuggestionPrompt(
            id = "summarize",
            title = "Summarize",
            subtitle = "Condense long text and articles",
            promptText = "Summarize the key differences between cloud AI APIs and local-first on-device inference."
        ),
        SuggestionPrompt(
            id = "write",
            title = "Write",
            subtitle = "Draft outlines, emails, and essays",
            promptText = "Draft a clear project plan for integrating llama.cpp GGUF execution on Android."
        ),
        SuggestionPrompt(
            id = "code",
            title = "Code",
            subtitle = "Generate code and debug logic",
            promptText = "Write a Kotlin coroutine flow example demonstrating token streaming."
        )
    )

    private val _uiState = MutableStateFlow(
        ChatUiState(
            selectedModel = settingsRepository.selectedModel.value,
            availableModels = settingsRepository.availableModels.value,
            suggestions = defaultSuggestions
        )
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.selectedModel.collect { model ->
                _uiState.update { it.copy(selectedModel = model) }
            }
        }
        viewModelScope.launch {
            settingsRepository.availableModels.collect { models ->
                _uiState.update { it.copy(availableModels = models) }
            }
        }
    }

    fun onInputTextChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onClearInput() {
        _uiState.update { it.copy(inputText = "") }
    }

    fun onSuggestionSelected(suggestion: SuggestionPrompt) {
        _uiState.update {
            it.copy(
                inputText = suggestion.promptText,
                infoMessage = null
            )
        }
    }

    fun onSendClicked() {
        val currentInput = _uiState.value.inputText.trim()
        if (currentInput.isEmpty()) return

        // Per STEP 1 requirements: Do not simulate AI and do not call cloud APIs.
        // Clearly communicate the Step 1 architectural boundary to the user.
        _uiState.update {
            it.copy(
                infoMessage = "Step 1 Foundation: Input captured. Local llama.cpp engine will process prompts in Step 2."
            )
        }
    }

    fun onDismissInfo() {
        _uiState.update { it.copy(infoMessage = null) }
    }

    fun setModelSelectorVisible(visible: Boolean) {
        _uiState.update { it.copy(isModelSelectorVisible = visible) }
    }

    fun onModelSelected(model: AiModel) {
        settingsRepository.setSelectedModel(model)
        _uiState.update {
            it.copy(
                selectedModel = model,
                isModelSelectorVisible = false
            )
        }
    }

    fun onNewConversation() {
        _uiState.update {
            it.copy(
                inputText = "",
                infoMessage = null
            )
        }
    }
}
