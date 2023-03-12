package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.apiFlow
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GitHubSearchRepository
 * 検索APIを叩きに行くところをラップ
 */
@Singleton
class GitHubSearchRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource) {

    suspend fun search(keyword: String): Flow<Resource<GitHubSearchResponse>> =
        apiFlow {
            remoteDataSource.searchRepositories(keyword)
        }
}