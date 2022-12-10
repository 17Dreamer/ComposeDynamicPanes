package com.dreamtech.dynamicpanes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.dreamtech.dynamicpanes.lib.*
import com.dreamtech.dynamicpanes.lib.navigation.ActionItem
import com.dreamtech.dynamicpanes.lib.navigation.NavigationItem
//import com.dreamtech.dynamicpanes.lib.ActionItem
//import com.dreamtech.dynamicpanes.lib.NavigationItem
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
                        panel = ListDetails(
                            listScreen = { modifier, _, navigateToDetails ->
//                                Column(modifier = modifier.fillMaxSize()) {
//                                    Text(
//                                        text = "List Screen",
//                                        style = MaterialTheme.typography.headlineLarge
//                                    )
//                                    Button(onClick = { navigateToDetails("Test") }) {
//                                        Text(text = "Open Details")
//                                    }
//                                }

                                ListScreen(modifier = modifier, navigateToDetails = navigateToDetails)
                            },
                            detailsScreen = { modifier, closeDetails ->
//                                Column(modifier = modifier.fillMaxSize()) {
//                                    Text(
//                                        text = "Details Screen",
//                                        style = MaterialTheme.typography.headlineLarge
//                                    )
//                                    Button(onClick = { closeDetails() }) {
//                                        Text(text = "Back To List")
//                                    }
//                                }
                                DetailsScreen(modifier = modifier)
                            }
                        )

                    ),
                    NavigationItem(
                        title = "SupportingPanel",
                        icon = painterResource(id = R.drawable.ic_supporting_panel),
                        panel = SupportingPanel(
                            primaryScreen = { modifier ->
//                                Text(
//                                    modifier = modifier,
//                                    text = "Main Content",
//                                    style = MaterialTheme.typography.headlineLarge
//                                )
                                PrimaryScreen(modifier = modifier)
                            },
                            supportingScreen = { modifier ->
//                                Text(
//                                    modifier = modifier,
//                                    text = "Supporting content",
//                                    style = MaterialTheme.typography.headlineLarge
//                                )
                                SupportingScreen(modifier = modifier)
                            }
                        )

                    ), NavigationItem(
                        title = "Feed",
                        icon = painterResource(id = R.drawable.ic_feed),
                        panel = Feed(
                            screenContent = { modifier ->
//                                Text(
//                                    modifier = modifier,
//                                    text = "Content",
//                                    style = MaterialTheme.typography.headlineLarge
//                                )
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
