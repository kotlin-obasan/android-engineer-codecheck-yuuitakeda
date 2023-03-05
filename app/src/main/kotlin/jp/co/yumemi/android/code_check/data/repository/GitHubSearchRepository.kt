package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.apiFlow
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class GitHubSearchRepository(
    private val remoteDataSource: RemoteDataSource,
) {
    fun search(keyword: String): Flow<Resource<GitHubSearchResponse>> =
        apiFlow {
            remoteDataSource.searchRepositories(keyword)
        }
}