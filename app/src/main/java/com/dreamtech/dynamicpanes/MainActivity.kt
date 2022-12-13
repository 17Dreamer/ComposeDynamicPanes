package com.dreamtech.dynamicpanes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.dreamtech.dynamicpanes.lib.*
import com.dreamtech.dynamicpanes.lib.navigation.ActionItem
import com.dreamtech.dynamicpanes.lib.navigation.NavigationItem
import com.dreamtech.dynamicpanes.ui.theme.DynamicPanesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicPanesTheme {

                val navigationItems = listOf(

                    NavigationItem(
                        title = "ListDetails",
                        icon = painterResource(id = R.drawable.ic_list_details),
                        primaryAction = ActionItem(
                            "Do Action", rememberVectorPainter(
                                image = Icons.Filled.Edit
                            )
                        ),
                        actionClicked = {
                            Toast.makeText(
                                this,
                                "Primary Action Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        panel = ListDetails(
                            listScreen = { modifier, _, navigateToDetails ->

                                ListScreen(
                                    modifier = modifier,
                                    navigateToDetails = navigateToDetails
                                )
                            },
                            detailsScreen = { modifier, _ ->
                                DetailsScreen(modifier = modifier)
                            }
                        )

                    ),
                    NavigationItem(
                        title = "SupportingPanel",
                        icon = painterResource(id = R.drawable.ic_supporting_panel),
                        panel = SupportingPanel(
                            primaryScreen = { modifier ->
                                PrimaryScreen(modifier = modifier)
                            },
                            supportingScreen = { modifier ->
                                SupportingScreen(modifier = modifier)
                            }
                        )

                    ), NavigationItem(
                        title = "Feed",
                        icon = painterResource(id = R.drawable.ic_feed),
                        panel = Feed(
                            screenContent = { modifier ->
                                FeedScreen(modifier = modifier)
                            }
                        )

                    )
                )


                DynamicPanes(navigationItems)
            }
        }
    }
}
