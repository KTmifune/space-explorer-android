package com.example.spaceexplorerapp.common

/**
 * Sep(SpaceExplorerApp)Result
 * アプリ全体で使用される汎用的な結果クラス。
 * 成功、エラー、読み込み中の各状態を表現し、
 * アプリ固有のクラスではなく、UI／ドメイン／データ層のいずれでも汎用的に使用するめ Common パッケージに配置。
 */
sealed class SepResult<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : SepResult<T>(data = data)
    class Error<T>(error: String) : SepResult<T>(error = error)
    class Loading<T> : SepResult<T>()
}
