package com.nexvora.ai.domain.model

/**
 * Suggested quick-start prompts displayed on the empty-state chat screen.
 */
data class SuggestionPrompt(
    val id: String,
    val title: String,
    val subtitle: String,
    val promptText: String
)
