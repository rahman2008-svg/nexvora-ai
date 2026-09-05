package com.nexvora.ai.ui.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexvora.ai.ui.components.NexVoraNavigationDrawerContent
import com.nexvora.ai.ui.screens.chat.ChatScreen
import com.nexvora.ai.ui.screens.chat.ChatViewModel
import com.nexvora.ai.ui.screens.conversations.ConversationsScreen
import com.nexvora.ai.ui.screens.documents.DocumentsScreen
import com.nexvora.ai.ui.screens.models.ModelsScreen
import com.nexvora.ai.ui.screens.search.SearchScreen
import com.nexvora.ai.ui.screens.settings.SettingsScreen
import com.nexvora.ai.ui.screens.settings.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun NexVoraNavHost(
    chatViewModel: ChatViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.Chat.route

    val chatUiState by chatViewModel.uiState.collectAsStateWithLifecycle()
    val settingsUiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentRoute == NavRoutes.Chat.route,
        drawerContent = {
            NexVoraNavigationDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    coroutineScope.launch { drawerState.close() }
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                },
                onNewChatClick = {
                    coroutineScope.launch { drawerState.close() }
                    chatViewModel.onNewConversation()
                    if (currentRoute != NavRoutes.Chat.route) {
                        navController.navigate(NavRoutes.Chat.route) {
                            popUpTo(NavRoutes.Chat.route) { inclusive = true }
                        }
                    }
                }
            )
        },
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Chat.route
        ) {
            composable(NavRoutes.Chat.route) {
                ChatScreen(
                    uiState = chatUiState,
                    onInputTextChange = chatViewModel::onInputTextChange,
                    onClearInput = chatViewModel::onClearInput,
                    onSuggestionClick = chatViewModel::onSuggestionSelected,
                    onSendClick = chatViewModel::onSendClicked,
                    onDismissInfo = chatViewModel::onDismissInfo,
                    onOpenDrawer = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onOpenSettings = {
                        navController.navigate(NavRoutes.Settings.route)
                    },
                    onShowModelSelector = {
                        chatViewModel.setModelSelectorVisible(true)
                    },
                    onHideModelSelector = {
                        chatViewModel.setModelSelectorVisible(false)
                    },
                    onModelSelect = chatViewModel::onModelSelected
                )
            }

            composable(NavRoutes.Conversations.route) {
                ConversationsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(NavRoutes.Models.route) {
                ModelsScreen(
                    models = chatUiState.availableModels,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(NavRoutes.Documents.route) {
                DocumentsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(NavRoutes.Search.route) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(NavRoutes.Settings.route) {
                SettingsScreen(
                    uiState = settingsUiState,
                    onBackClick = { navController.popBackStack() },
                    onThemeModeChange = settingsViewModel::setThemeMode,
                    onGpuAccelerationChange = settingsViewModel::setGpuAcceleration,
                    onClearCache = settingsViewModel::clearCache,
                    onDismissNotification = settingsViewModel::dismissNotification
                )
            }
        }
    }
}
