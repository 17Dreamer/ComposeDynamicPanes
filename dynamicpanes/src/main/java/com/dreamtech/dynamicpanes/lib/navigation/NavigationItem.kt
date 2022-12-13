package com.dreamtech.dynamicpanes.lib.navigation

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.Painter
import com.dreamtech.dynamicpanes.lib.ScreenPanel

@Stable
data class NavigationItem(
    val title: String,
    val route: String = title,
    val icon: Painter,
    val selectedIcon: Painter = icon,
    val primaryAction: ActionItem? = null,
    val actionClicked: () -> Unit = {},
    val panel: ScreenPanel
)


data class ActionItem(
    val label : String,
    val icon: Painter
)