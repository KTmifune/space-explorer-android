package com.example.spaceexplorerapp.ui.feature.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorerapp.common.SepResult
import com.example.spaceexplorerapp.domain.model.ApodInfo
import com.example.spaceexplorerapp.domain.usecase.GetAstronomyPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val getAstronomyPicture: GetAstronomyPictureUseCase
) : ViewModel() {

    private val dateFlow = MutableStateFlow<String?>(null)

    init {
        // 初期ロード
        refresh()
    }

    val uiState: StateFlow<ApodUiState> = dateFlow
        .flatMapLatest { date ->
            getAstronomyPicture(date)
                .map { result ->
                    when (result) {
                        is SepResult.Loading -> ApodUiState.Loading
                        is SepResult.Success -> ApodUiState.Success(result.data ?: ApodInfo())
                        is SepResult.Error -> ApodUiState.Error(result.error ?: "")
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApodUiState.Loading
        )

    private fun refresh(date: String? = null) {
        dateFlow.value = date
    }


}