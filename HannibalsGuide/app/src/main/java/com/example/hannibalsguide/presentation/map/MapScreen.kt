package com.example.hannibalsguide.presentation.map

import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hannibalsguide.presentation.localization.UiStrings
import com.example.hannibalsguide.presentation.settings.LanguageViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.material3.Icon
import com.example.hannibalsguide.presentation.components.TunisianPatternBackground
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    landmarkId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val landmarks by viewModel.landmarks.collectAsState()
    val landmark = landmarks.find { it.id == landmarkId }
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val language by languageViewModel.language.collectAsState()
    val strings = UiStrings(language)

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences("osmdroid_prefs", 0)
        )
        Configuration.getInstance().userAgentValue = context.packageName
    }

    if (landmark == null) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(strings.mapTitle) },
                    navigationIcon = {
                        FilledIconButton(
                            onClick = onBack,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = strings.backContentDescription
                            )
                        }
                    }
                )
            }
        ) { padding ->
            TunisianPatternBackground(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = strings.locationNotAvailable,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(landmark.name) },
                navigationIcon = {
                    FilledIconButton(
                        onClick = onBack,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = strings.backContentDescription
                        )
                    }
                }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            factory = { ctx ->
                MapView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(13.0)

                    val point = GeoPoint(landmark.lat, landmark.lng)
                    controller.setCenter(point)

                    val marker = Marker(this).apply {
                        position = point
                        title = landmark.name
                        subDescription = landmark.city
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    }
                    overlays.add(marker)
                }
            },
            update = { mapView ->
                val point = GeoPoint(landmark.lat, landmark.lng)
                mapView.controller.setCenter(point)
                mapView.overlays.clear()

                val marker = Marker(mapView).apply {
                    position = point
                    title = landmark.name
                    subDescription = landmark.city
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                mapView.overlays.add(marker)
                mapView.invalidate()
            }
        )
    }
}