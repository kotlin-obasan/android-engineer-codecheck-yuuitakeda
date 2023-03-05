package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.source.RemoteDataSource

class GitHubSearchRepository(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun searchGitHubRepositories(keyword: String) {
        
    }
}