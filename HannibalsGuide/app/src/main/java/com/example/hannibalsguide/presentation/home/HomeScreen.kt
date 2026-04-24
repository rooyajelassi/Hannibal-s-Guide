package com.example.hannibalsguide.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.presentation.components.LandmarkItem

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
        topBar = { TopAppBar(title = { Text("Hannibal's Guide") }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                label = { Text("Search by name or city") },
                singleLine = true
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
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

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    val previewLandmarks = listOf(
        Landmark(
            id = "1",
            name = "El Jem Amphitheatre",
            city = "El Jem",
            description = "Roman amphitheatre in Tunisia.",
            history = "Built in the 3rd century AD.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg",
            images = listOf("https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg"),
            lat = 35.2964,
            lng = 10.7069
        )
    )

    HomeScreenContent(
        landmarks = previewLandmarks,
        query = "",
        onQueryChange = {},
        onLandmarkClick = {}
    )
}