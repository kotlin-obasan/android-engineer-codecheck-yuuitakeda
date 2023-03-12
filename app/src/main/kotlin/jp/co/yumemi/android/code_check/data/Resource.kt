package jp.co.yumemi.android.code_check.data

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class DataError(val error: Throwable) : Resource<Nothing>()
//    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)
    class Loading<T>() : Resource<T>()

}
