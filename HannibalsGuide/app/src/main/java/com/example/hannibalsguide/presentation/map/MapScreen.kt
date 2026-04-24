package com.example.hannibalsguide.presentation.map

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    landmarkId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val landmark = viewModel.getLandmarkLocation(landmarkId)

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences("osmdroid_prefs", 0)
        )
        Configuration.getInstance().userAgentValue = context.packageName
    }

    if (landmark == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Map") },
                    navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
                )
            }
        ) { padding ->
            Text(
                text = "Location not available",
                modifier = Modifier.fillMaxSize()
            )
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(landmark.name) },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
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