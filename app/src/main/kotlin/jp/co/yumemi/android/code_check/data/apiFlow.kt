package jp.co.yumemi.android.code_check.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<Resource<T>> =
    flow<Resource<T>> {
        val response = call()
        if(response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(data = it))
            } ?: emit(Resource.DataError(500)) //レスポンスbodyがnullだったらサーバエラーと見なす
        }
        else emit(Resource.DataError(response.code()))
    }.onStart {
        emit(Resource.Loading(data = null))
    }.flowOn(Dispatchers.IO)
