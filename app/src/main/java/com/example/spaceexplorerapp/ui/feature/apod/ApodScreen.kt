package com.example.spaceexplorerapp.ui.feature.apod

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spaceexplorerapp.ui.designsystem.theme.SpaceExplorerAppTheme
import com.example.spaceexplorerapp.ui.preview.DevicePreviews

@Composable
fun ApodScreen(
    modifier: Modifier = Modifier,
    viewModel: ApodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ApodScreen(state = uiState, modifier = modifier)
}

@Composable
private fun ApodScreen(
    state: ApodUiState,
    modifier: Modifier = Modifier
) {

    val isLoading = state is ApodUiState.Loading

    // This code should be called when the UI is ready for use and relates to Time To Full Display.
    ReportDrawnWhen { !isLoading }

    when (state) {
        is ApodUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is ApodUiState.Error -> {
            val msg = state.message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = msg)
            }
        }

        is ApodUiState.Success -> {
            val photo = state.photo
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    AsyncImage(
                        model = photo.imageUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(photo.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text(photo.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ApodScreenPreview() {
    SpaceExplorerAppTheme {
        ApodScreen(
            state = ApodUiState.Loading
        )
    }
}

