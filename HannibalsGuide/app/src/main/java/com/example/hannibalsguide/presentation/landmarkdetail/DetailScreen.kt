package com.example.hannibalsguide.presentation.landmarkdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hannibalsguide.domain.model.Landmark


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    landmarkId: String,
    onBack: () -> Unit,
    onAskTarek: (String) -> Unit,
    onOpenMap: (String) -> Unit
) {
    val landmark by viewModel.landmark.collectAsState()
    LaunchedEffect(landmarkId) { viewModel.loadLandmark(landmarkId) }

    val lm = landmark
    if (lm == null) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Landmark details") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                verticalArrangement = Arrangement.Center
            ) { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
        }
        return
    }

    DetailContent(
        landmark = lm,
        onBack = onBack,
        onAskTarek = onAskTarek,
        onOpenMap = onOpenMap
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    landmark: Landmark,
    onBack: () -> Unit,
    onAskTarek: (String) -> Unit,
    onOpenMap: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(landmark.name) },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Ask Tarek") },
                icon = { Icon(Icons.Filled.Info, contentDescription = "Ask Tarek") },
                onClick = { onAskTarek(landmark.id) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(12.dp)
        ) {
            Text(text = landmark.city, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(landmark.images) { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = landmark.name,
                        modifier = Modifier.size(220.dp, 140.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Description", style = MaterialTheme.typography.titleMedium)
            Text(landmark.description)

            Spacer(modifier = Modifier.height(12.dp))
            Text("History", style = MaterialTheme.typography.titleMedium)
            Text(landmark.history)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onOpenMap(landmark.id) }) { Text("View on Map") }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailContentPreview() {
    val previewLandmark = Landmark(
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

    DetailContent(
        landmark = previewLandmark,
        onBack = {},
        onAskTarek = {},
        onOpenMap = {}
    )
}