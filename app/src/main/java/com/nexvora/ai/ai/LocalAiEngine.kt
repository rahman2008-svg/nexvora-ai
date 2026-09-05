package com.nexvora.ai.ai

import com.nexvora.ai.domain.model.AiModel
import kotlinx.coroutines.flow.Flow

/**
 * Architectural contract for the on-device local AI inference engine.
 *
 * In STEP 1: Defines the interface contract and boundaries.
 * In STEP 2: Will be implemented by a local llama.cpp JNI wrapper loading GGUF models.
 *
 * No dummy or simulated AI implementations are present in STEP 1.
 */
interface LocalAiEngine {
    val isModelLoaded: Boolean
    val currentModel: AiModel?

    /**
     * Future stream contract for token-by-token local generation via llama.cpp.
     */
    fun generateStream(prompt: String): Flow<String>

    /**
     * Future model lifecycle management for loading GGUF weights.
     */
    suspend fun loadModel(model: AiModel): Result<Unit>
    suspend fun unloadModel()
}
