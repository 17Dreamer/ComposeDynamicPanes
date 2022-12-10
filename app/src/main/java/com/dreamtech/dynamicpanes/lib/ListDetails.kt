package com.dreamtech.dynamicpanes.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(modifier: Modifier = Modifier, navigateToDetails: (Any) -> Unit) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier.padding(vertical = 10.dp)) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {}
            }
            items(10) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(2.dp)
                        .clickable { navigateToDetails("") },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {}
            }
        }
    }
}

@Composable
fun DetailsScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes
                    .small
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(end = 10.dp, top = 10.dp, start = 100.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )

        ) {
        }
        Box(
            modifier = Modifier
                .height(50.dp)
                .width(230.dp)
                .padding(10.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(end = 10.dp, top = 10.dp, start = 110.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )

        ) {
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {
            Row(modifier = Modifier.height(50.dp)) {

            }
        }
    }
}