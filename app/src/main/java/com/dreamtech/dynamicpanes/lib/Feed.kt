package com.dreamtech.dynamicpanes.lib

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(modifier: Modifier = Modifier) {
    LazyVerticalStaggeredGrid(modifier = modifier.padding(10.dp), columns = StaggeredGridCells.Adaptive(250.dp)) {

        items(10) {
            FeedItem()
        }
    }
}

@Composable
fun FeedItem() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically){
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .padding(8.dp)
            )
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)) {
                Box(
                    modifier = Modifier
                        .height(10.dp)
                        .width(150.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.large
                        )
                        .padding(5.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(110.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.shapes.large
                        )
                        .padding(5.dp)
                )
            }
        }

        val spacerHeight = Random.nextInt(100) + 150
        Spacer(modifier = Modifier.height(spacerHeight.dp))

        Row(modifier = Modifier.padding(5.dp)){
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(5.dp)
            ){
                Text(text = "*", fontSize = 8.sp)
            }

            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(5.dp)
            ){
                Text(text = "**", fontSize = 8.sp)
            }
        }
    }

}
