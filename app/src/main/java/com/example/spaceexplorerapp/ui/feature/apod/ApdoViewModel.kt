package com.example.spaceexplorerapp.ui.feature.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorerapp.common.SepResult
import com.example.spaceexplorerapp.domain.model.ApodInfo
import com.example.spaceexplorerapp.domain.usecase.GetAstronomyPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val getAstronomyPicture: GetAstronomyPictureUseCase
) : ViewModel() {

    // ① UI 状態を保持する StateFlow
    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    init {
        // ② 画面起動時に一度だけロード
        fetchData()
    }

    /** 画面起動時＆プル・リフレッシュ時に呼ぶ */
    fun fetchData() {
        viewModelScope.launch {
            // ──「前回 Success の状態」──
            val prevPhoto = (_uiState.value as? ApodUiState.Success)?.photo

            val date = randomDateWithinLastWeek()
            // ③ Flow を collect して結果を逐次反映
            getAstronomyPicture(date.toString()).collect { result ->
                _uiState.value = when (result) {
                    is SepResult.Loading ->
                        if (prevPhoto != null)
                        //「前回 Success の状態があればRefreshing状態」
                            ApodUiState.Refreshing(prevPhoto)
                        else
                            ApodUiState.Loading

                    is SepResult.Success -> ApodUiState.Success(result.data ?: ApodInfo())
                    is SepResult.Error -> ApodUiState.Error(result.error.orEmpty())
                }
            }
        }
    }

    /**
     * 本日から遡って最大7日前までの間で、ランダムな日付を返す関数。
     * @return ランダムな LocalDate (YYYY-MM-DD)
     */
    private fun randomDateWithinLastWeek(): LocalDate {
        // ① 今日の日付を取得
        val today = LocalDate.now()

        // ② 1週間前の日付を計算
        val weekAgo = today.minusDays(7)

        // ③ 1週間前から今日までの日数差（7日）を取得
        val daysBetween = ChronoUnit.DAYS.between(weekAgo, today).toInt()

        // ④ 0 から daysBetween の範囲でランダムなオフセット日数を生成
        //    IntRange.random() は Kotlin 1.3+ の標準拡張メソッド :contentReference[oaicite:1]{index=1}
        val randomOffset = (0..daysBetween).random()

        // ⑤ 1週間前 + randomOffset 日を返す
        return weekAgo.plusDays(randomOffset.toLong())
    }


}