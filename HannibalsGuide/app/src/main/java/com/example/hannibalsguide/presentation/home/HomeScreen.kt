package com.example.hannibalsguide.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.presentation.components.LandmarkItem
import com.example.hannibalsguide.presentation.components.TunisianPatternBackground
import com.example.hannibalsguide.ui.theme.HannibalsGuideTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLandmarkClick: (String) -> Unit
) {
    val landmarks by viewModel.landmarks.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.loadLandmarks() }

    HomeScreenContent(
        landmarks = landmarks,
        query = query,
        onQueryChange = {
            query = it
            viewModel.search(it)
        },
        onLandmarkClick = onLandmarkClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    landmarks: List<Landmark>,
    query: String,
    onQueryChange: (String) -> Unit,
    onLandmarkClick: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Discover Tunisia") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        TunisianPatternBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    placeholder = { Text("Search by name, city, or heritage") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Search") },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(landmarks) { landmark ->
                        LandmarkItem(
                            landmark = landmark,
                            onClick = { onLandmarkClick(landmark.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    HannibalsGuideTheme {
        HomeScreenContent(
            landmarks = emptyList(),
            query = "",
            onQueryChange = {},
            onLandmarkClick = {}
        )
    }
}