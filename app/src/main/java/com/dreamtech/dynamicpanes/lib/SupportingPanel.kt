package com.dreamtech.dynamicpanes.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes
                    .small
            )
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.7f)
            .padding(20.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            ))
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.1f)
            .padding(start = 20.dp, end = 40.dp, bottom = 40.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.extraSmall
            ))

    }
}

@Composable
fun SupportingScreen(modifier: Modifier = Modifier){
    LazyVerticalGrid(modifier = modifier.padding(top = 10.dp, end = 10.dp, bottom = 10.dp), columns = GridCells.Fixed(2)){
        items(2) {
            Box(modifier = Modifier
                .height(25.dp)
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                ))
        }
        items(30){
            Box(modifier = Modifier
                .height(50.dp)
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.extraSmall
                ))
        }
    }
}