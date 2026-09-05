package com.nexvora.ai.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexvora.ai.domain.model.ThemeMode
import com.nexvora.ai.ui.components.NexVoraLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onThemeModeChange: (ThemeMode) -> Unit,
    onGpuAccelerationChange: (Boolean) -> Unit,
    onClearCache: () -> Unit,
    onDismissNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.statusNotification) {
        uiState.statusNotification?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            onDismissNotification()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Section 1: AI Engine
            SettingsSectionHeader(
                title = "AI ENGINE",
                icon = Icons.Default.Speed
            )
            SettingsCard {
                SettingsInfoRow(
                    title = "Inference Runtime",
                    subtitle = "llama.cpp (Local C++ / JNI)",
                    badge = "Step 2 Ready"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "CPU Inference Threads",
                    subtitle = "${uiState.cpuThreads} Threads (Optimized for battery & heat)",
                    badge = "Active"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "Context Window",
                    subtitle = "${uiState.contextSize} Tokens",
                    badge = "Default"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "GPU Acceleration (Vulkan)",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Accelerates token generation via local GPU compute",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.gpuAcceleration,
                        onCheckedChange = onGpuAccelerationChange,
                        modifier = Modifier.testTag("gpu_acceleration_switch")
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section 2: Models
            SettingsSectionHeader(
                title = "MODELS",
                icon = Icons.Default.Memory
            )
            SettingsCard {
                SettingsInfoRow(
                    title = "Active Local Model",
                    subtitle = uiState.selectedModel.name,
                    badge = uiState.selectedModel.quantization
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "Model Storage Directory",
                    subtitle = "Internal App Storage (files/models/)",
                    badge = "Sandboxed"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "Quantization Standard",
                    subtitle = "GGUF format (4-bit & 8-bit quantized weights)",
                    badge = "GGUF"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section 3: Appearance
            SettingsSectionHeader(
                title = "APPEARANCE",
                icon = Icons.Default.Palette
            )
            SettingsCard {
                ThemeOptionItem(
                    title = "System Default",
                    description = "Follow Android device system theme",
                    selected = uiState.themeMode == ThemeMode.SYSTEM,
                    onClick = { onThemeModeChange(ThemeMode.SYSTEM) },
                    testTag = "theme_system"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                ThemeOptionItem(
                    title = "Light Theme",
                    description = "Clean, crisp light canvas",
                    selected = uiState.themeMode == ThemeMode.LIGHT,
                    onClick = { onThemeModeChange(ThemeMode.LIGHT) },
                    testTag = "theme_light"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                ThemeOptionItem(
                    title = "Dark Theme",
                    description = "Modern deep slate & indigo contrast",
                    selected = uiState.themeMode == ThemeMode.DARK,
                    onClick = { onThemeModeChange(ThemeMode.DARK) },
                    testTag = "theme_dark"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section 4: Storage
            SettingsSectionHeader(
                title = "STORAGE",
                icon = Icons.Default.SdCard
            )
            SettingsCard {
                SettingsInfoRow(
                    title = "Chat History & Cache",
                    subtitle = "Current footprint: ${uiState.storageUsage}",
                    badge = "Local"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Clear Local Cache",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Purge temporary inference cache and logs",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    OutlinedButton(
                        onClick = onClearCache,
                        modifier = Modifier.testTag("clear_cache_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CleaningServices,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Clear")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section 5: About
            SettingsSectionHeader(
                title = "ABOUT",
                icon = Icons.Default.Info
            )
            SettingsCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NexVoraLogo(size = 44.dp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "NexVora AI",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Version 1.0.0 (Step 1 Foundation)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "Architecture",
                    subtitle = "100% Local-First · Zero Cloud AI APIs",
                    badge = "Private"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsInfoRow(
                    title = "Privacy Guarantee",
                    subtitle = "No telemetry, no remote servers, no user tracking",
                    badge = "Verified"
                )
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
private fun SettingsSectionHeader(
    title: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            content()
        }
    }
}

@Composable
private fun SettingsInfoRow(
    title: String,
    subtitle: String,
    badge: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = badge,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun ThemeOptionItem(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
