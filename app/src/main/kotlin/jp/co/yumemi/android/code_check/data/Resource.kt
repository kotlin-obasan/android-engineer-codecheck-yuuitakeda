package jp.co.yumemi.android.code_check.data

/**
 * Resource
 * APIの結果とデータそのものを返すSealedClass
 *
 * Success: 成功
 * DataError: 何らかのエラー
 * Loading: 読込中
 */
sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class DataError(val error: Throwable) : Resource<Nothing>()
    class Loading<T> : Resource<T>()

}
