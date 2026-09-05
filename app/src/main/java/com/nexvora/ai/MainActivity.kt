package com.nexvora.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexvora.ai.ui.navigation.NexVoraNavHost
import com.nexvora.ai.ui.screens.chat.ChatViewModel
import com.nexvora.ai.ui.screens.settings.SettingsViewModel
import com.nexvora.ai.ui.theme.NexVoraTheme

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsUiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

            NexVoraTheme(themeMode = settingsUiState.themeMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NexVoraNavHost(
                        chatViewModel = chatViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
