package com.dreamtech.dynamicpanes.lib

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.dreamtech.dynamicpanes.lib.navigation.NavigationItem
import com.dreamtech.dynamicpanes.lib.utils.ContentType
import com.dreamtech.dynamicpanes.lib.utils.NavigationType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy

@Composable
internal fun AppContentScreen(
    navigationItem: NavigationItem,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()

    if (contentType == ContentType.DUAL_PANE && navigationItem.panel !is Feed) {

        TwoPane(
            first = {
                if(navigationItem.panel is ListDetails){
                    navigationItem.panel.listScreen(Modifier, lazyListState) {}
                }else if(navigationItem.panel is SupportingPanel){
                    navigationItem.panel.primaryScreen(Modifier)
                }
            },
            second = {
                if(navigationItem.panel is ListDetails){
                    navigationItem.panel.detailsScreen(Modifier) {}
                }else if(navigationItem.panel is SupportingPanel){
                    navigationItem.panel.supportingScreen(Modifier)
                }
            },
            strategy = if(navigationItem.panel is ListDetails) HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp) else HorizontalTwoPaneStrategy(splitFraction = 0.7f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            var isDetailOnlyOpen by remember { mutableStateOf(false) }
            /**
             * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
             */

            LaunchedEffect(key1 = contentType) {
                if (!isDetailOnlyOpen) {
                    isDetailOnlyOpen = false
                }
            }
            SinglePaneContent(
                navigationItem = navigationItem,
                isDetailOnlyOpen = isDetailOnlyOpen,
                lazyListState = lazyListState,
                displayFeatures = displayFeatures,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = {
                    isDetailOnlyOpen = false
                },
                navigateToDetail = {
                    isDetailOnlyOpen = true
                }
            )
            navigationItem.primaryAction?.let { action ->
                // When we have bottom navigation we show FAB at the bottom end.
                if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            painter = action.icon,
                            contentDescription = action.label,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SinglePaneContent(
    navigationItem: NavigationItem,
    isDetailOnlyOpen: Boolean,
    lazyListState: LazyListState,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Any) -> Unit
) {
    when(navigationItem.panel){
        is ListDetails ->{
            if (isDetailOnlyOpen) {
                BackHandler {
                    closeDetailScreen()
                }
                navigationItem.panel.detailsScreen.invoke(modifier, closeDetailScreen)
            } else {
                navigationItem.panel.listScreen(modifier, lazyListState, navigateToDetail)
            }
        }
        is SupportingPanel ->{
            TwoPane(
                first = {
                    navigationItem.panel.primaryScreen(Modifier)
                },
                second = {
                    navigationItem.panel.supportingScreen(Modifier)
                },
                strategy = VerticalTwoPaneStrategy(splitFraction = 0.6f, gapHeight = 16.dp),
                displayFeatures = displayFeatures
            )
        }
        is Feed ->{
            navigationItem.panel.screenContent(Modifier)
        }
    }
}

