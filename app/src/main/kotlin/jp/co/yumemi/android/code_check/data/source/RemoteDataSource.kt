package jp.co.yumemi.android.code_check.data.source

import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * RemoteDataSource
 * 各APIのパスなどの設定を持つインターフェース
 */
interface RemoteDataSource {

    // GitHubリポジトリを検索
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): Response<GitHubSearchResponse>
}
