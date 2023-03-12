package jp.co.yumemi.android.code_check.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.Response

/**
 * apiFlow
 * Response型をラップしてResource型を出力
 * こうすることで例外が直接UI側に伝搬してしまうのを防ぐ
 */
inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<Resource<T>> =
    flow {
        val response = call()
        if(response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(data = it))
            } ?: emit(Resource.DataError(HttpException(null))) //レスポンスbodyがnullならエラーと見なす
        }
        else emit(Resource.DataError(HttpException(response)))
    }.onStart {
        emit(Resource.Loading())
    }.flowOn(Dispatchers.IO)
