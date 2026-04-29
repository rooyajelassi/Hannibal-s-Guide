package com.example.hannibalsguide.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.presentation.localization.UiStrings
import com.example.hannibalsguide.presentation.components.TunisianPatternBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: LanguageViewModel,
    onBack: () -> Unit
) {
    val language by viewModel.language.collectAsState()
    val strings = UiStrings(language)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(strings.settingsTitle) },
                navigationIcon = {
                    FilledIconButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) {
                        androidx.compose.material3.Icon(
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = strings.languageLabel, style = MaterialTheme.typography.titleMedium)

                val options = listOf(
                    AppLanguage.ENGLISH to strings.englishLabel,
                    AppLanguage.FRENCH to strings.frenchLabel,
                    AppLanguage.ARABIC to strings.arabicLabel
                )

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(options) { option ->
                        val isSelected = option.first == language
                        androidx.compose.material3.Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { viewModel.setLanguage(option.first) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                androidx.compose.foundation.layout.Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { viewModel.setLanguage(option.first) }
                                    )
                                    Text(text = option.second, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

