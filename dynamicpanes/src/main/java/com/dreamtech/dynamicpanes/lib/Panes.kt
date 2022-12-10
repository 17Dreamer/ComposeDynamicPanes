package com.dreamtech.dynamicpanes.lib

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class ScreenPanel protected constructor()

data class Feed(val screenContent: @Composable (Modifier) -> Unit) : ScreenPanel()

data class SupportingPanel(
    val primaryScreen: @Composable (Modifier) -> Unit,
    val supportingScreen: @Composable (Modifier) -> Unit
) : ScreenPanel()

data class ListDetails(
    val listScreen: @Composable (Modifier, LazyListState, (Any)->Unit)->Unit,
    val detailsScreen: @Composable (Modifier, ()->Unit)->Unit
) : ScreenPanel()