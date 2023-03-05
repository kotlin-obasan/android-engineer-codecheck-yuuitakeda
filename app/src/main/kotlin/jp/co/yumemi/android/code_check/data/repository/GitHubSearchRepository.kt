package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.source.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubSearchRepository(
    private val remoteDataSource: RemoteDataSource,
) {

//    suspend fun search(keyword: String): Resource<GitHubRepositoryInfo> {
//
//    }
}