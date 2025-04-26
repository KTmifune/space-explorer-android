package com.example.spaceexplorerapp.domain.usecase

import com.example.spaceexplorerapp.common.SepResult
import com.example.spaceexplorerapp.domain.model.ApodInfo
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAstronomyPictureUseCase @Inject constructor(
    private val repository: NasaApiRepository
) {
    /**
     * 指定したパラメータで天文写真を取得します。
     *
     * @param date 単一の日付（"YYYY-MM-DD"形式）。null の場合は startDate/endDate または count が優先される。
     * @param startDate 取得開始日。null の場合は単一 date を使用。
     * @param endDate 取得終了日。null の場合は単一 date を使用。
     * @param count ランダム取得数。null の場合は日付指定モード。
     * @param thumbs サムネイル URL も取得するかどうか。
     * @return Flow<Result<ApodPhoto>> ロード中・成功・失敗を含むストリーム
     */
    operator fun invoke(
        date: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        count: Int? = null,
        thumbs: Boolean = false
    ): Flow<SepResult<ApodInfo>> = flow {
        // まずロード状態を通知
        emit(SepResult.Loading())

        // リポジトリ経由でデータ取得
        val photo =
            repository.getApodInfo(date, startDate, endDate, count, thumbs)

        if (photo != null) {
            emit(SepResult.Success(photo))
        } else {
            emit(SepResult.Error("データが取得できませんでした"))
        }
    }.catch { e ->
        // 例外が起きたら Error で通知
        emit(SepResult.Error(e.message ?: "不明なエラーが発生しました"))
    }
}