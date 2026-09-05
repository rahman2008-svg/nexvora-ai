package com.nexvora.ai.ui.screens.chat

import com.nexvora.ai.domain.model.AiModel
import com.nexvora.ai.domain.model.SuggestionPrompt

data class ChatUiState(
    val inputText: String = "",
    val selectedModel: AiModel,
    val availableModels: List<AiModel> = emptyList(),
    val suggestions: List<SuggestionPrompt> = emptyList(),
    val isModelSelectorVisible: Boolean = false,
    val infoMessage: String? = null
) {
    val isSendEnabled: Boolean
        get() = inputText.trim().isNotEmpty()
}
