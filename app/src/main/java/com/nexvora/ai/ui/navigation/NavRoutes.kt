package com.nexvora.ai.ui.navigation

/**
 * Type-safe navigation routes for NexVora AI.
 */
sealed class NavRoutes(val route: String) {
    data object Chat : NavRoutes("chat")
    data object Conversations : NavRoutes("conversations")
    data object Models : NavRoutes("models")
    data object Documents : NavRoutes("documents")
    data object Search : NavRoutes("search")
    data object Settings : NavRoutes("settings")
}
