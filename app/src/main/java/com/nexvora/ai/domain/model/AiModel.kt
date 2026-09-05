package com.nexvora.ai.domain.model

/**
 * Representation of an on-device local AI model.
 * Prepared for STEP 2 GGUF / llama.cpp model loading.
 */
data class AiModel(
    val id: String,
    val name: String,
    val parameterSize: String,
    val quantization: String,
    val diskSize: String,
    val description: String,
    val isRecommended: Boolean = false,
    val isAvailableOffline: Boolean = false
)
