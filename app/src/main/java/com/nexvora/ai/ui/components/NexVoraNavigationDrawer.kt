package com.nexvora.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexvora.ai.ui.navigation.NavRoutes

@Composable
fun NexVoraNavigationDrawerContent(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onNewChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier.width(320.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                NexVoraLogo(size = 38.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "NexVora AI",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Local-First Intelligence",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // New Conversation Action
            Button(
                onClick = onNewChatClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("drawer_new_chat_button"),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "New Conversation",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "NAVIGATION",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )

            // Navigation Items
            DrawerNavigationItem(
                label = "Chat",
                icon = Icons.AutoMirrored.Filled.Chat,
                selected = currentRoute == NavRoutes.Chat.route,
                onClick = { onNavigate(NavRoutes.Chat.route) },
                testTag = "nav_item_chat"
            )

            DrawerNavigationItem(
                label = "Conversations",
                icon = Icons.Default.Forum,
                selected = currentRoute == NavRoutes.Conversations.route,
                onClick = { onNavigate(NavRoutes.Conversations.route) },
                testTag = "nav_item_conversations"
            )

            DrawerNavigationItem(
                label = "Local Models",
                icon = Icons.Default.Memory,
                selected = currentRoute == NavRoutes.Models.route,
                onClick = { onNavigate(NavRoutes.Models.route) },
                testTag = "nav_item_models"
            )

            DrawerNavigationItem(
                label = "Documents",
                icon = Icons.Default.Description,
                selected = currentRoute == NavRoutes.Documents.route,
                onClick = { onNavigate(NavRoutes.Documents.route) },
                testTag = "nav_item_documents"
            )

            DrawerNavigationItem(
                label = "Search",
                icon = Icons.Default.Search,
                selected = currentRoute == NavRoutes.Search.route,
                onClick = { onNavigate(NavRoutes.Search.route) },
                testTag = "nav_item_search"
            )

            DrawerNavigationItem(
                label = "Settings",
                icon = Icons.Default.Settings,
                selected = currentRoute == NavRoutes.Settings.route,
                onClick = { onNavigate(NavRoutes.Settings.route) },
                testTag = "nav_item_settings"
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))

            // Footer Architecture Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "STEP 1 FOUNDATION",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "On-device runtime ready for llama.cpp & GGUF models in Step 2.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerNavigationItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                )
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        },
        selected = selected,
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 2.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
