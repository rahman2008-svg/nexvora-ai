package com.nexvora.ai.ui.screens.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexvora.ai.domain.model.AiModel
import com.nexvora.ai.domain.model.SuggestionPrompt
import com.nexvora.ai.ui.components.ModelSelectorBottomSheet
import com.nexvora.ai.ui.components.NexVoraLogo
import com.nexvora.ai.ui.components.NexVoraTopBar
import com.nexvora.ai.ui.components.SuggestionCard
import com.nexvora.ai.ui.theme.NexVoraGlowCyan
import com.nexvora.ai.ui.theme.NexVoraGlowIndigo

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onInputTextChange: (String) -> Unit,
    onClearInput: () -> Unit,
    onSuggestionClick: (SuggestionPrompt) -> Unit,
    onSendClick: () -> Unit,
    onDismissInfo: () -> Unit,
    onOpenDrawer: () -> Unit,
    onOpenSettings: () -> Unit,
    onShowModelSelector: () -> Unit,
    onHideModelSelector: () -> Unit,
    onModelSelect: (AiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.infoMessage) {
        uiState.infoMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            onDismissInfo()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            NexVoraTopBar(
                selectedModel = uiState.selectedModel,
                onMenuClick = onOpenDrawer,
                onModelSelectorClick = onShowModelSelector,
                onSettingsClick = onOpenSettings
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 80.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main content area - Empty State with suggestions
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Branding Hero Header
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        // Ambient glow backdrop
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            NexVoraGlowCyan.copy(alpha = 0.25f),
                                            NexVoraGlowIndigo.copy(alpha = 0.15f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                        NexVoraLogo(size = 64.dp)
                    }

                    Text(
                        text = "NexVora AI",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "How can I help you?",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Local-First Architecture · Zero Cloud Tracking",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Suggested Actions Section Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 600.dp)
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Suggested Prompts",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Suggestion Cards (Explain, Summarize, Write, Code)
                items(uiState.suggestions, key = { it.id }) { suggestion ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 600.dp)
                            .padding(vertical = 5.dp)
                    ) {
                        SuggestionCard(
                            suggestion = suggestion,
                            onClick = {
                                onSuggestionClick(suggestion)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tap a suggestion to populate the prompt box.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Bottom Chat Input Dock
            ChatInputDock(
                inputText = uiState.inputText,
                isSendEnabled = uiState.isSendEnabled,
                selectedModelName = uiState.selectedModel.name,
                onInputChange = onInputTextChange,
                onClearClick = onClearInput,
                onSendClick = {
                    focusManager.clearFocus()
                    onSendClick()
                },
                onModelClick = onShowModelSelector,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
                    .navigationBarsPadding()
            )
        }
    }

    if (uiState.isModelSelectorVisible) {
        ModelSelectorBottomSheet(
            models = uiState.availableModels,
            selectedModel = uiState.selectedModel,
            onModelSelect = onModelSelect,
            onDismiss = onHideModelSelector
        )
    }
}

@Composable
private fun ChatInputDock(
    inputText: String,
    isSendEnabled: Boolean,
    selectedModelName: String,
    onInputChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSendClick: () -> Unit,
    onModelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .widthIn(max = 640.dp),
        shape = RoundedCornerShape(26.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Input TextField
                OutlinedTextField(
                    value = inputText,
                    onValueChange = onInputChange,
                    placeholder = {
                        Text(
                            text = "Ask NexVora AI anything...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (isSendEnabled) onSendClick()
                        }
                    )
                )

                if (inputText.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("chat_clear_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear input",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Bottom bar inside dock: Model pill on left, Send button on right
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quick model pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .testTag("chat_dock_model_badge")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = selectedModelName,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Send Button
                IconButton(
                    onClick = onSendClick,
                    enabled = isSendEnabled,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSendEnabled) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send message",
                        tint = if (isSendEnabled) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
