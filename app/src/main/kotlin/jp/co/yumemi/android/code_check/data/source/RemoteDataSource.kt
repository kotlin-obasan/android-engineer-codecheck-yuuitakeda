package jp.co.yumemi.android.code_check.data.source

import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    // GitHubリポジトリを検索
    @GET("search/repositories")
    suspend fun searchRepoositories(@Query("q") query: String): Response<GitHubSearchResponse>
}
