package com.example.hannibalsguide.presentation.landmarkdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.presentation.components.TunisianPatternBackground
import com.example.hannibalsguide.presentation.localization.UiStrings
import com.example.hannibalsguide.presentation.settings.LanguageViewModel
import com.example.hannibalsguide.ui.theme.HannibalsGuideTheme
import androidx.hilt.navigation.compose.hiltViewModel

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
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val language by languageViewModel.language.collectAsState()
    val strings = UiStrings(language)
    LaunchedEffect(landmarkId) { viewModel.loadLandmark(landmarkId) }

    val lm = landmark
    if (lm == null) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(strings.landmarkDetailsTitle) },
                    navigationIcon = {
                        FilledIconButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = strings.backContentDescription)
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        return
    }

    DetailContent(
        landmark = lm,
        strings = strings,
        onBack = onBack,
        onAskTarek = onAskTarek,
        onOpenMap = onOpenMap
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    landmark: Landmark,
    strings: UiStrings,
    onBack: () -> Unit,
    onAskTarek: (String) -> Unit,
    onOpenMap: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(landmark.name) },
                navigationIcon = {
                    FilledIconButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = strings.backContentDescription)
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ElevatedButton(
                    onClick = { onAskTarek(landmark.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Rounded.ChatBubbleOutline, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(strings.askTarek)
                }
                Button(
                    onClick = { onOpenMap(landmark.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Rounded.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(strings.viewOnMap)
                }
            }
        }
    ) { padding ->
        TunisianPatternBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = landmark.city, style = MaterialTheme.typography.titleMedium)

                val gallery = landmark.images.filter { it.isNotBlank() }
                    .ifEmpty { listOf(landmark.imageUrl).filter { it.isNotBlank() } }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(gallery) { image ->
                        Card(shape = RoundedCornerShape(16.dp)) {
                            AsyncImage(
                                model = image,
                                contentDescription = landmark.name,
                                modifier = Modifier.size(260.dp, 165.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                HeritageChips(strings)
                InfoCard(strings.culturalStory, landmark.description)
                InfoCard(strings.echoesOfHistory, landmark.history)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HeritageChips(strings: UiStrings) {
    val chips = strings.heritageChips
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(chips) { chip ->
            AssistChip(onClick = {}, label = { Text(chip) })
        }
    }
}

@Composable
private fun InfoCard(title: String, text: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(text = text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    HannibalsGuideTheme {
        DetailContent(
            landmark = Landmark(
                id = "1",
                name = "El Jem Amphitheatre",
                city = "El Jem",
                description = "Long cultural description...",
                history = "Long historical background...",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg",
                images = listOf(
                    "https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg"
                ),
                lat = 35.2964,
                lng = 10.7069,
                category = "Historical Site"
            ),
            strings = UiStrings(com.example.hannibalsguide.domain.model.AppLanguage.ENGLISH),
            onBack = {},
            onAskTarek = {},
            onOpenMap = {}
        )
    }
}