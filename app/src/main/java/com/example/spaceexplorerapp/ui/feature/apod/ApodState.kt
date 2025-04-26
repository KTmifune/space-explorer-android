package com.example.spaceexplorerapp.ui.feature.apod

import com.example.spaceexplorerapp.domain.model.ApodInfo

sealed interface ApodUiState {
    data object Loading : ApodUiState
    data class Success(val photo: ApodInfo) : ApodUiState
    data class Error(val message: String) : ApodUiState
}