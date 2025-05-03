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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spaceexplorerapp.domain.model.ApodInfo
import com.example.spaceexplorerapp.ui.designsystem.theme.SpaceExplorerAppTheme
import com.example.spaceexplorerapp.ui.preview.DevicePreviews

@Composable
fun ApodScreen(
    modifier: Modifier = Modifier,
    viewModel: ApodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ApodScreen(state = uiState, modifier = modifier, onRefresh = viewModel::fetchData)
}

@Composable
private fun ApodScreen(
    state: ApodUiState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {

    val isLoading = state is ApodUiState.Loading

    // This code should be called when the UI is ready for use and relates to Time To Full Display.
    ReportDrawnWhen { !isLoading }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ApodUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ApodUiState.Error -> {
                val msg = state.message
                Text(text = msg)
            }

            is ApodUiState.Refreshing -> {
                ApodContent(
                    data = state.photo,
                    isRefreshing = true,
                    onRefresh = onRefresh
                )
            }

            is ApodUiState.Success -> {

                ApodContent(
                    data = state.photo,
                    isRefreshing = false,
                    onRefresh = onRefresh
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ApodContent(
    data: ApodInfo,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                AsyncImage(
                    model = data.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(12.dp))
                Text(data.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text(data.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

}

@DevicePreviews
@Composable
private fun ApodScreenPreview() {
    SpaceExplorerAppTheme {

    }
}

