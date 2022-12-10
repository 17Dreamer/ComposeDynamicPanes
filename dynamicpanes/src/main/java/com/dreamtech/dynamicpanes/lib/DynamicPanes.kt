package com.dreamtech.dynamicpanes.lib

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.dreamtech.dynamicpanes.lib.navigation.*
import com.dreamtech.dynamicpanes.lib.utils.*
import com.google.accompanist.adaptive.calculateDisplayFeatures
import kotlinx.coroutines.launch
import java.security.InvalidParameterException

/**
 *
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DynamicPanes(
    navigationItems: List<NavigationItem> = emptyList()
) {
    if(navigationItems.isEmpty()){
        throw InvalidParameterException("navigationItems is Empty!")
    }
    val context = LocalContext.current
    context.getActivity()?.let {
        val windowSize = calculateWindowSizeClass(it)
        val displayFeatures = calculateDisplayFeatures(it)

        val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

        val foldingDevicePosture = when {
            isBookPosture(foldingFeature) ->
                DevicePosture.BookPosture(foldingFeature.bounds)

            isSeparating(foldingFeature) ->
                DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

            else -> DevicePosture.NormalPosture
        }

        /**
         * This will help us select type of navigation and content type depending on window size and
         * fold state of the device.
         */
        val navigationType: NavigationType
        val contentType: ContentType

        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                navigationType = NavigationType.BOTTOM_NAVIGATION
                contentType = ContentType.SINGLE_PANE
            }
            WindowWidthSizeClass.Medium -> {
                navigationType = NavigationType.NAVIGATION_RAIL
                contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                    ContentType.DUAL_PANE
                } else {
                    ContentType.SINGLE_PANE
                }
            }
            WindowWidthSizeClass.Expanded -> {
                navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                    NavigationType.NAVIGATION_RAIL
                } else {
                    NavigationType.PERMANENT_NAVIGATION_DRAWER
                }
                contentType = ContentType.DUAL_PANE
            }
            else -> {
                navigationType = NavigationType.BOTTOM_NAVIGATION
                contentType = ContentType.SINGLE_PANE
            }
        }

        /**
         * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
         * ergonomics and reachability depending upon the height of the device.
         */
        val navigationContentPosition = when (windowSize.heightSizeClass) {
            WindowHeightSizeClass.Compact -> {
                NavigationContentPosition.TOP
            }
            WindowHeightSizeClass.Medium,
            WindowHeightSizeClass.Expanded -> {
                NavigationContentPosition.CENTER
            }
            else -> {
                NavigationContentPosition.TOP
            }
        }

        if(navigationItems.size > 1) {

            navigationItems.forEach { navigationItem ->
                routesMap[navigationItem.route] = navigationItem
            }

            AppNavigationWrapper(
                navigationItems = navigationItems,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition
            )
        }else{
            AppContentScreen(
                navigationItem = navigationItems[0],
                contentType = contentType,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                modifier = Modifier
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppNavigationWrapper(
    navigationItems: List<NavigationItem>,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route
    val selectedDestination = routesMap[currentRoute] ?: navigationItems[0]
    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                navigationItems = navigationItems,
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
            )
        }) {
            AppContent(
                navigationItems = navigationItems,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    navigationItems = navigationItems,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            AppContent(
                navigationItems = navigationItems,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    }
}


@Composable
private fun AppContent(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem>,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: NavigationItem,
    navigateToTopLevelDestination: (NavigationItem) -> Unit = {},
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            AppNavigationRail(
                navigationItems = navigationItems,
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            AppNavHost(
                navigationItems = navigationItems,
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationType = navigationType,
                modifier = Modifier.weight(1f),
            )
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(
                    navigationItems = navigationItems,
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
private fun AppNavHost(
    navigationItems: List<NavigationItem>,
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = navigationItems[0].route,
    ) {
        navigationItems.forEach { navigationItem ->
            composable(navigationItem.route) {
                AppContentScreen(
                    navigationItem = navigationItem,
                    contentType = contentType,
                    navigationType = navigationType,
                    displayFeatures = displayFeatures,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

val routesMap = hashMapOf<String, NavigationItem>()